package net.laaber.marsweather.weather;

import java.time.LocalDate;

public record WeatherResponse(LocalDate earthDate, int sol, WeatherInfo weather) {}
