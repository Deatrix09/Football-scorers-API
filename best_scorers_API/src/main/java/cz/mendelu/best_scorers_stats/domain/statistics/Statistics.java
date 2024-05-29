package cz.mendelu.best_scorers_stats.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistics {

    private double consistency_index_for_all_players;
    private double average_impact_score_for_all_players;
    private double average_player_efficiency_for_all_players;
    private int count_deleted_players;


}
