package cz.mendelu.best_scorers_stats.domain.club;

import cz.mendelu.best_scorers_stats.domain.league.League;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

public interface ClubRepository extends CrudRepository<Club, Long>{

}
