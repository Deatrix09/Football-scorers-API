package cz.mendelu.best_scorers_stats.domain.club;

import cz.mendelu.best_scorers_stats.domain.country.CountryService;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }


    public Optional<Club> findById(Long id) {
        return clubRepository.findById(id);
    }


    public List<Club> getAllClubs() {
        List<Club> clubs = new ArrayList<>();
        clubRepository.findAll().forEach(clubs::add);
        return clubs;
    }


    public Optional<Club> findClubById(Long id) {
        return clubRepository.findById(id);
    }

    public void deleteClub(Long  clubId) {
        clubRepository.deleteById(clubId);
    }

    public Club createClub(Club club) {
        return clubRepository.save(club);
    }
}
