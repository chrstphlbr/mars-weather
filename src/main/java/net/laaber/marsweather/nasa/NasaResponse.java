package net.laaber.marsweather.nasa;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record NasaResponse(
        @JsonProperty("descriptions") Map<String, String> descriptions,
        @JsonProperty("soles") List<SolInfo> soles) {}
