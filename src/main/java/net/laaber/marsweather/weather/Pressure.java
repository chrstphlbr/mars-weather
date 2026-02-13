package net.laaber.marsweather.weather;

import java.util.Optional;

public record Pressure(Optional<Integer> value, String description) {}
