package net.laaber.marsweather.nasa;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record SolInfo(
        @JsonProperty("id") int id,
        @JsonProperty("terrestrial_date") LocalDate terrestrialDate,
        @JsonProperty("sol") int sol,
        @JsonProperty("ls") int solarLongitude,
        @JsonProperty("season") String season,
        @JsonProperty("min_temp") Integer minAirTemperature,
        @JsonProperty("max_temp") Integer maxAirTemperature,
        @JsonProperty("pressure") Integer atmosphericPressure,
        @JsonProperty("pressure_string") String atmosphericPressureString,
        @JsonProperty("abs_humidity") Integer absoluteHumidity,
        @JsonProperty("wind_speed") Integer windSpeed,
        @JsonProperty("wind_direction") String windDirection,
        @JsonProperty("atmo_opacity") String atmosphericOpacity,
        @JsonProperty("sunrise") String sunrise, // not LocalTime because Mars days have 24h40m
        @JsonProperty("sunset") String sunset, // not LocalTime because Mars days have 24h40m
        @JsonProperty("local_uv_irradiance_index") String localUvIrradianceIndex,
        @JsonProperty("min_gts_temp") Integer minGroundTemperature,
        @JsonProperty("max_gts_temp") Integer maxGroundTemperature) {}
