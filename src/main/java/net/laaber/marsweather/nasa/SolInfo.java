package net.laaber.marsweather.nasa;

import java.time.LocalDate;

public record SolInfo(
        int id,
        LocalDate terrestrialDate,
        int sol,
        int ls,
        String season,
        int minTemp,
        int maxTemp,
        int pressure,
        String pressureString,
        String absHumidity,
        String windSpeed,
        String windDirection,
        String atmoOpacity,
        String sunrise, // not LocalTime because Mars days have 24h40m
        String sunset, // not LocalTime because Mars days have 24h40m
        String localUvIrradianceIndex,
        int minGtsTemp,
        int maxGtsTemp
) {
}
