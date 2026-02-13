package net.laaber.marsweather.nasa;

public record SolInfo(
        String id,
        String terrestrialDate,
        String sol,
        String ls,
        String season,
        String minTemp,
        String maxTemp,
        String pressure,
        String pressureString,
        String absHumidity,
        String windSpeed,
        String windDirection,
        String atmoOpacity,
        String sunrise, // not LocalTime because Mars days have 24h40m
        String sunset, // not LocalTime because Mars days have 24h40m
        String localUvIrradianceIndex,
        String minGtsTemp,
        String maxGtsTemp) {}
