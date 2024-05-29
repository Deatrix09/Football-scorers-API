package cz.mendelu.best_scorers_stats.domain.player;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long>{


    @Query("SELECT p FROM Player p WHERE p.club_id = ?1")
    List<Player> getPlayersByClub_id(Club club);

    @Query("SELECT p FROM Player p WHERE p.matches_played > ?1")
    List<Player> findPlayersByMatchesPlayedMoreThan(Long matchesPlayed);


    @Query("SELECT p FROM Player p WHERE p.year = ?1 ORDER BY p.goals_scored DESC")
    List<Player> findTop3ByYearOrderByGoalsScoredDesc(Long year, Pageable topThree);

    @Transactional
    @Modifying
    @Query("DELETE FROM Player p WHERE p.minutes_played < :minMinutesPlayed")
    int deleteByMinutesPlayedLessThan(@Param("minMinutesPlayed") long minMinutesPlayed);
}
