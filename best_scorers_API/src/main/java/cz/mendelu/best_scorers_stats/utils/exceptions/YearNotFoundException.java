package cz.mendelu.best_scorers_stats.utils.exceptions;

public class YearNotFoundException extends RuntimeException {
    public YearNotFoundException(int year) {
        super("No players found for the year: " + year);
    }
}
