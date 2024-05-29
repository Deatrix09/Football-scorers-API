package cz.mendelu.best_scorers_stats.domain.statistics;


import cz.mendelu.best_scorers_stats.domain.club.ClubRepository;
import cz.mendelu.best_scorers_stats.domain.player.Player;
import cz.mendelu.best_scorers_stats.domain.player.PlayerRepository;
import cz.mendelu.best_scorers_stats.domain.statistics.utils.PlayerEfficiency;
import cz.mendelu.best_scorers_stats.utils.exceptions.YearNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StatisticsService {

    private final PlayerRepository playerRepository;

    private final ClubRepository clubRepository;



    public StatisticsService(
            PlayerRepository playerRepository,
            ClubRepository clubRepository
    ) {
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }

    /**
     * Vrátí 3 nejlepší hráče s nejvyšší hráčskou efektivitou v daném roce.
     * Hráčská efektivita je vypočítána následovně:
     * Hráčská efektivita = (goals_scored + expected_goals) / (matches_played + count_of_substitutions)
     * × (shots_on_target / shots)
     * × (minutes_played / 90)
     *
     * @param year rok, podle kterého se hráči filtrují
     * @return seznam 3 nejlepších hráčů s nejvyšší hráčskou efektivitou
     */
    public List<PlayerEfficiency> getTop3PlayerEfficiencies(int year) {
        var players = StreamSupport.stream(playerRepository.findAll().spliterator(), false)
                .filter(player -> player.getYear() == year)
                .toList();

        if (players.isEmpty()) {
            throw new YearNotFoundException(year);
        }

        return players.stream()
                .map(player -> new PlayerEfficiency(
                        player.getFull_name(),
                        calculatePlayerEfficiency(player),
                        player.getYear()
                ))
                .sorted((p1, p2) -> Double.compare(p2.getEfficiency(), p1.getEfficiency()))
                .limit(3)
                .collect(Collectors.toList());
    }

    public double calculatePlayerEfficiency(Player player) {
        double matchesPlayed = player.getMatches_played();
        double countOfSubstitutions = player.getCount_of_substitutions();
        double minutesPlayed = player.getMinutes_played();
        double goalsScored = player.getGoals_scored();
        double expectedGoals = player.getExpected_goals();
        double shots = player.getShots();
        double shotsOnTarget = player.getShots_on_target();

        if (matchesPlayed + countOfSubstitutions == 0 || shots == 0) {
            return 0;
        }

        return (goalsScored + expectedGoals) / (matchesPlayed + countOfSubstitutions) *
                (shotsOnTarget / shots) *
                (minutesPlayed / 90);
    }

    /**
     * Vypočítá consistency index pro seznam hráčů.
     * -
     * Consistency index je měřen pomocí směrodatné odchylky gólů na zápas.
     *
     * @param players Seznam objektů Player pro které se má vypočítat consistency index.
     * @return Směrodatná odchylka gólů na zápas.
     */
    public double calculateConsistencyIndex(List<Player> players) {
        double totalGoalsPerMatch = 0;
        double totalGoalsPerMatchSquared = 0;
        int totalPlayers = players.size();

        for (Player player : players) {
            double goalsPerMatch = (double) player.getGoals_scored() / player.getMatches_played();
            totalGoalsPerMatch += goalsPerMatch;
            totalGoalsPerMatchSquared += Math.pow(goalsPerMatch, 2);
        }

        double meanGoalsPerMatch = totalGoalsPerMatch / totalPlayers;
        double variance = (totalGoalsPerMatchSquared / totalPlayers) - Math.pow(meanGoalsPerMatch, 2);
        return Math.sqrt(variance);
    }


    /**
     * Vypočítá průměrný impact score pro seznam hráčů.
     * -
     * Impact score zohledňuje jak počet gólů, tak počet střídání, preferující hráče, kteří skórují i přes časté střídání.
     *
     * @param players Seznam objektů Player pro které se má vypočítat průměrný impact score.
     * @return Průměrný impact score všech hráčů.
     */
    public double calculateAverageImpact(List<Player> players) {
        double totalImpact = 0;

        for (Player player : players) {
            double impact = player.getGoals_scored() * (1 + (double) player.getCount_of_substitutions() / player.getMatches_played());
            totalImpact += impact;
        }

        return totalImpact / players.size();
    }

    /**
     * Vypočítá průměrnou efektivitu hráčů.
     * -
     * Efektivita hráče je vypočítána následovně:
     * Hráčská efektivita = (goals_scored + expected_goals) / (matches_played + count_of_substitutions)
     * × (shots_on_target / shots)
     * × (minutes_played / 90)
     *
     * @param players Seznam objektů Player pro které se má vypočítat průměrná efektivita.
     * @return Průměrná efektivita všech hráčů.
     */
    public double calculateAveragePlayerEfficiency(List<Player> players) {
        double totalEfficiency = 0;
        int totalPlayers = players.size();

        for (Player player : players) {
            totalEfficiency += calculatePlayerEfficiency(player);
        }

        return totalPlayers == 0 ? 0 : totalEfficiency / totalPlayers;
    }

    /**
     * Smaže všechny hráče, kteří mají méně odehraných minut než je zadaný limit.
     *
     * @param players Seznam objektů Player, které se mají filtrovat.
     * @param minMinutesPlayed Minimální počet odehraných minut, který musí hráč mít, aby nebyl smazán.
     * @return Počet smazaných hráčů.
     */
    public int filterAndCountDeletedPlayers(List<Player> players, long minMinutesPlayed) {
        return playerRepository.deleteByMinutesPlayedLessThan(minMinutesPlayed);
    }




    public Statistics getStatistics() {

        List<Player> players = StreamSupport.stream(playerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return new Statistics(
                calculateConsistencyIndex(players),
                calculateAverageImpact(players),
                calculateAveragePlayerEfficiency(players),
                filterAndCountDeletedPlayers(players, 1600)
        );
    }
}
