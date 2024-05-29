package cz.mendelu.best_scorers_stats.domain.statistics;

import cz.mendelu.best_scorers_stats.domain.statistics.utils.PlayerEfficiency;
import cz.mendelu.best_scorers_stats.utils.exceptions.YearNotFoundException;
import cz.mendelu.best_scorers_stats.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("statistics")
@Validated
@Tag(
        name = "Statistics"
)
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Operation(
            summary = "Get statistics"
    )
    @GetMapping(value = "", produces = "application/json")

    public ObjectResponse<Statistics> getStatistics() {
        var statistics = statisticsService.getStatistics();
        return new ObjectResponse<>(statistics, 1);
    }


    @Operation(
            summary = "Get top 3 players by efficiency"
    )
    @GetMapping(value = "/top3/{year}", produces = "application/json")
    public ObjectResponse<List<PlayerEfficiency>> getTop3PlayersByEfficiency(@PathVariable int year) {
        var top3PlayerEfficiencies = statisticsService.getTop3PlayerEfficiencies(year);
        return new ObjectResponse<>(top3PlayerEfficiencies, 1);
    }



    /**
     * Exception handler for YearNotFoundException
     * @param ex exception
     * @return response with message and status code
     */
    @ExceptionHandler(YearNotFoundException.class)
    public ResponseEntity<ObjectResponse<String>> handleYearNotFoundException(YearNotFoundException ex) {
        ObjectResponse<String> response = new ObjectResponse<>(ex.getMessage(), 0);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
