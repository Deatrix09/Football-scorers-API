package cz.mendelu.best_scorers_stats.domain.player;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import cz.mendelu.best_scorers_stats.utils.response.ArrayResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player>  findById(Long id) {
        return playerRepository.findById(id);
    }

    public List<Player> findAllPlayers() {
        List<Player> players = new ArrayList<>();;

        playerRepository.findAll().forEach(players::add);
        return players;
    }


    public List<Player> getPlayersByClub(Club club) {
        return playerRepository.getPlayersByClub_id(club);

    }

    public List<Player> findPlayersByMatchesPlayedMoreThan(Long matchesPlayed) {
        return playerRepository.findPlayersByMatchesPlayedMoreThan(matchesPlayed);
    }

    public List<Player> getTop3ScorersByYear(Long year) {
        Pageable topThree = PageRequest.of(0, 3);
        return playerRepository.findTop3ByYearOrderByGoalsScoredDesc(year, topThree);
    }

    public Player updatePlayer(Player player, Long id) {
        player.setId(id);
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }


    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }
}
