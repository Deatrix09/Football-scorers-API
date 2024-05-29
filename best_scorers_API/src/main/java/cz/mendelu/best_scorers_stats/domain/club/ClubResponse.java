package cz.mendelu.best_scorers_stats.domain.club;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ClubResponse {

    @NotNull
    private Long id;

    private String club_code;

    public ClubResponse(Club club) {
        this.id = club.getId();
        this.club_code = club.getClub_code();
    }


}
