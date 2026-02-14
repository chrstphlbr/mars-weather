package net.laaber.marsweather.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import io.restassured.RestAssured;
import java.time.LocalDate;
import net.laaber.marsweather.shared.exception.InvalidDateException;
import net.laaber.marsweather.shared.exception.NoWeatherForSolException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerTest {

    @LocalServerPort
    private int port;

    @MockitoBean
    private WeatherService weatherService;

    private static final LocalDate TEST_DATE = LocalDate.of(2026, 2, 7);

    private static final int TEST_SOL = 4802;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void getWeatherWithValidDate() {
        var weatherInfo = new WeatherInfo(
                new Temperature(-67, 2),
                new Temperature(-83, 12),
                new Pressure(807, "Higher"),
                "Sunny",
                "Moderate",
                new Wind(null, null),
                null);
        var weatherResponse = new WeatherResponse(TEST_DATE, TEST_SOL, weatherInfo);
        when(weatherService.getWeather(TEST_DATE)).thenReturn(weatherResponse);

        var response = RestAssured.given()
                .param("date", TEST_DATE.toString())
                .when()
                .get("/weather")
                .then()
                .statusCode(200)
                .extract()
                .response();

        assertThat(response.jsonPath().getString("earthDate")).isEqualTo(TEST_DATE.toString());
        assertThat(response.jsonPath().getInt("sol")).isEqualTo(TEST_SOL);
        assertThat(response.jsonPath().getObject("weather", Object.class)).isNotNull();
        assertThat(response.jsonPath().getInt("weather.airTemperature.min")).isEqualTo(-67);
        assertThat(response.jsonPath().getInt("weather.airTemperature.max")).isEqualTo(2);
    }

    @Test
    void getWeatherWithFutureDateReturnsBadRequest() {
        var futureDate = LocalDate.now().plusDays(1);
        when(weatherService.getWeather(futureDate)).thenThrow(new InvalidDateException("date is after today"));

        var response = RestAssured.given()
                .param("date", futureDate.toString())
                .when()
                .get("/weather")
                .then()
                .statusCode(400)
                .extract()
                .response();

        assertThat(response.jsonPath().getString("title")).isEqualTo("Invalid date");
        assertThat(response.jsonPath().getString("detail")).isEqualTo("date is after today");
    }

    @Test
    void getWeatherWithNoDataReturnsNotFound() {
        var pastDate = LocalDate.of(2012, 8, 6);
        when(weatherService.getWeather(pastDate)).thenThrow(new NoWeatherForSolException("no weather info for sol 0"));

        var response = RestAssured.given()
                .param("date", pastDate.toString())
                .when()
                .get("/weather")
                .then()
                .statusCode(404)
                .extract()
                .response();

        assertThat(response.jsonPath().getString("title")).isEqualTo("No weather info");
        assertThat(response.jsonPath().getString("detail")).isEqualTo("no weather info for sol 0");
    }
}
