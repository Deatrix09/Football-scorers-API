package cz.mendelu.best_scorers_stats.domain.league;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public League createLeague(League league) {
        return leagueRepository.save(league);
    }


    public List<League> getAllLeagues() {
        List<League> leagues = new ArrayList<>();
        leagueRepository.findAll().forEach(leagues::add);
        return leagues;
    }


    public Optional<League> getLeagueById(Long id) {
        return leagueRepository.findById(id);
    }

    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);
    }
}
