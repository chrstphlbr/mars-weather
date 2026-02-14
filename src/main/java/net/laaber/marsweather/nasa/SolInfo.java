package net.laaber.marsweather.nasa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SolInfo(
        @JsonProperty("id") String id,
        @JsonProperty("terrestrial_date") String terrestrialDate,
        @JsonProperty("sol") String sol,
        @JsonProperty("ls") String solarLongitude,
        @JsonProperty("season") String season,
        @JsonProperty("min_temp") String minAirTemperature,
        @JsonProperty("max_temp") String maxAirTemperature,
        @JsonProperty("pressure") String atmosphericPressure,
        @JsonProperty("pressure_string") String pressureString,
        @JsonProperty("abs_humidity") String absoluteHumidity,
        @JsonProperty("wind_speed") String windSpeed,
        @JsonProperty("wind_direction") String windDirection,
        @JsonProperty("atmo_opacity") String atmosphericOpacity,
        @JsonProperty("sunrise") String sunrise, // not LocalTime because Mars days have 24h40m
        @JsonProperty("sunset") String sunset, // not LocalTime because Mars days have 24h40m
        @JsonProperty("local_uv_irradiance_index") String localUvIrradianceIndex,
        @JsonProperty("min_gts_temp") String minGroundTemperature,
        @JsonProperty("max_gts_temp") String maxGroundTemperature) {}
