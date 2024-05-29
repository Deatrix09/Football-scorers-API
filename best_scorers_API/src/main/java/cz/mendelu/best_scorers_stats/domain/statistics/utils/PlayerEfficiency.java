package cz.mendelu.best_scorers_stats.domain.statistics.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerEfficiency {
    private String fullName;
    private double efficiency;
    private Long year;
}
