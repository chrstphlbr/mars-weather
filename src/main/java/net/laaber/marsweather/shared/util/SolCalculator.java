package net.laaber.marsweather.shared.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import net.laaber.marsweather.shared.exception.InvalidDateException;

public final class SolCalculator {
    private static final LocalDate CURIOSITY_LANDING = LocalDate.of(2012, 8, 6);
    private static final double SECONDS_PER_SOL = 88775.245;

    private SolCalculator() {}

    public static int from(LocalDate date) {
        long deltaDays = ChronoUnit.DAYS.between(CURIOSITY_LANDING, date);

        if (deltaDays < 0) {
            throw new InvalidDateException("date is before the Curiosity landing on %s".formatted(CURIOSITY_LANDING));
        }

        double deltaSeconds = deltaDays * 86400.0;
        return (int) Math.ceil(deltaSeconds / SECONDS_PER_SOL);
    }
}
