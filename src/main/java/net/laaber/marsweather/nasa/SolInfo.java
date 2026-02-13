package net.laaber.marsweather.nasa;

import java.time.LocalDate;
import java.util.Optional;

public record SolInfo(
        int id,
        LocalDate terrestrialDate,
        int sol,
        int ls,
        String season,
        int minTemp,
        int maxTemp,
        Optional<Integer> pressure,
        String pressureString,
        Optional<Integer> absHumidity,
        int windSpeed,
        String windDirection,
        String atmoOpacity,
        String sunrise, // not LocalTime because Mars days have 24h40m
        String sunset, // not LocalTime because Mars days have 24h40m
        String localUvIrradianceIndex,
        int minGtsTemp,
        int maxGtsTemp) {}
