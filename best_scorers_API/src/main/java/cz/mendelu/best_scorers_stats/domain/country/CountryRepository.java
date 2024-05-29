package cz.mendelu.best_scorers_stats.domain.country;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Long>{
    @Query("SELECT c FROM Country c WHERE c.country_name = :countryName")
    Optional<Country> findByName(String countryName);
}
