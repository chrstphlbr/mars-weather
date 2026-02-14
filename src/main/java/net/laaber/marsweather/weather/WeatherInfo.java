package net.laaber.marsweather.weather;

public record WeatherInfo(
        Temperature airTemperature,
        Temperature groundTemperature,
        Pressure atmosphericPressure,
        String atmosphericOpacity,
        String uvIrradianceIndex,
        Wind wind,
        Integer absoluteHumidity) {}
