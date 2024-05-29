package cz.mendelu.best_scorers_stats.domain.statistics;

import cz.mendelu.best_scorers_stats.domain.club.ClubRepository;
import cz.mendelu.best_scorers_stats.domain.player.Player;
import cz.mendelu.best_scorers_stats.domain.player.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsUnitTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        player1 = new Player();
        player1.setFull_name("Player 1");
        player1.setGoals_scored(10L);
        player1.setExpected_goals(5.0);
        player1.setMatches_played(20L);
        player1.setCount_of_substitutions(5L);
        player1.setShots(50L);
        player1.setShots_on_target(30L);
        player1.setMinutes_played(1800L);
        player1.setYear(2023L);

        player2 = new Player();
        player2.setFull_name("Player 2");
        player2.setGoals_scored(8L);
        player2.setExpected_goals(4.0);
        player2.setMatches_played(18L);
        player2.setCount_of_substitutions(4L);
        player2.setShots(40L);
        player2.setShots_on_target(25L);
        player2.setMinutes_played(1600L);
        player2.setYear(2023L);

        player3 = new Player();
        player3.setFull_name("Player 3");
        player3.setGoals_scored(12L);
        player3.setExpected_goals(6.0);
        player3.setMatches_played(22L);
        player3.setCount_of_substitutions(6L);
        player3.setShots(60L);
        player3.setShots_on_target(35L);
        player3.setMinutes_played(2000L);
        player3.setYear(2023L);
    }

    @Test
    void testCalculatePlayerEfficiency() {
        double efficiency = statisticsService.calculatePlayerEfficiency(player1);
        assertEquals(7.2, efficiency, 0.001);
    }

    @Test
    void testCalculateConsistencyIndex() {
        List<Player> players = Arrays.asList(player1, player2, player3);
        double consistencyIndex = statisticsService.calculateConsistencyIndex(players);
        assertEquals(0.04130, consistencyIndex, 0.001);
    }

    @Test
    void testCalculateAverageImpact() {
        List<Player> players = Arrays.asList(player1, player2, player3);
        double averageImpact = statisticsService.calculateAverageImpact(players);
        assertEquals(12.5168, averageImpact, 0.001);
    }

    @Test
    void testCalculateAveragePlayerEfficiency() {
        List<Player> players = Arrays.asList(player1, player2, player3);
        double averageEfficiency = statisticsService.calculateAveragePlayerEfficiency(players);
        assertEquals(7.1967, averageEfficiency, 0.002);
    }

    @Test
    void testFilterAndCountDeletedPlayers() {
        List<Player> players = Arrays.asList(player1, player2, player3);
        when(playerRepository.deleteByMinutesPlayedLessThan(1700L)).thenReturn(1);

        int deletedPlayers = statisticsService.filterAndCountDeletedPlayers(players, 1700L);
        assertEquals(1, deletedPlayers);
    }
}