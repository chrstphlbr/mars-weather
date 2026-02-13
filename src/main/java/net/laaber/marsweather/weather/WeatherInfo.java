package net.laaber.marsweather.weather;

import java.util.Optional;

public record WeatherInfo(
        Optional<Temperature> airTemperature,
        Optional<Temperature> groundTemperature,
        Optional<Pressure> atmosphericPressure,
        Optional<String> atmosphericOpacity,
        Optional<String> uvIrradianceIndex,
        Optional<Wind> wind,
        Optional<Integer> absoluteHumidity) {}
