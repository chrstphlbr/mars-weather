package net.laaber.marsweather.nasa;

import java.util.List;
import java.util.Map;

public record NasaResponse(Map<String, String> descriptions, List<SolInfo> soles) {}
