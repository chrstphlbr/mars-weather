package net.laaber.marsweather.shared.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class SolCalculator {
    public static final LocalDate CURIOSITY_LANDING = LocalDate.of(2012, 8, 6);
    private static final double SECONDS_PER_SOL = 88775.245;

    private SolCalculator() {}

    public static int from(LocalDate date) {
        long deltaDays = ChronoUnit.DAYS.between(CURIOSITY_LANDING, date);
        double deltaSeconds = deltaDays * 86400.0;
        return (int) Math.ceil(deltaSeconds / SECONDS_PER_SOL);
    }
}
