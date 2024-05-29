package cz.mendelu.best_scorers_stats.domain.country;

import cz.mendelu.best_scorers_stats.utils.exceptions.AlreadyExistsException;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().forEach(countries::add);
        return countries;
    }

    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    public Optional<Country> addCountry(Country country) {
        if (countryRepository.findByName(country.getCountry_name()).isPresent()) {
            return Optional.empty();
        }

        return Optional.of(countryRepository.save(country));
    }

    public void deleteCountry(Long country) {
        if (!countryRepository.existsById(country)) {
            throw new NotFoundException("Country with id " + country + " not found");
        }
        countryRepository.deleteById(country);
    }
}
