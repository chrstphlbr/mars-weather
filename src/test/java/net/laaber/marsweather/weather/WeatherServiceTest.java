package net.laaber.marsweather.weather;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import net.laaber.marsweather.nasa.NasaClient;
import net.laaber.marsweather.nasa.NasaResponse;
import net.laaber.marsweather.nasa.SolInfo;
import net.laaber.marsweather.shared.exception.InvalidDateException;
import net.laaber.marsweather.shared.exception.NoWeatherForSolException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private NasaClient nasaClient;

    @InjectMocks
    private WeatherService weatherService;

    private static final LocalDate TEST_DATE = LocalDate.of(2026, 2, 7);

    private static final int TEST_SOL = 4802;

    private static final SolInfo TEST_SOL_INFO = new SolInfo(
            4551,
            LocalDate.of(2026, 2, 7),
            4802,
            221,
            "Month 8",
            -67,
            2,
            807,
            "Higher",
            null,
            15,
            "NW",
            "Sunny",
            "05:23",
            "17:34",
            "Moderate",
            -83,
            12);

    @Test
    void getWeatherWithValidDateAndData() {
        var nasaResponse = new NasaResponse(null, List.of(TEST_SOL_INFO));
        when(nasaClient.getMarsWeather()).thenReturn(nasaResponse);

        var result = weatherService.getWeather(TEST_DATE);

        assertThat(result).isNotNull();
        assertThat(result.earthDate()).isEqualTo(TEST_DATE);
        assertThat(result.sol()).isEqualTo(TEST_SOL);
        assertThat(result.weather()).isNotNull();

        var weather = result.weather();
        assertThat(weather.airTemperature()).isNotNull();
        assertThat(weather.airTemperature().min()).isEqualTo(TEST_SOL_INFO.minAirTemperature());
        assertThat(weather.airTemperature().max()).isEqualTo(TEST_SOL_INFO.maxAirTemperature());
        assertThat(weather.groundTemperature()).isNotNull();
        assertThat(weather.atmosphericPressure()).isNotNull();
        assertThat(weather.wind()).isNotNull();
        assertThat(weather.atmosphericOpacity()).isEqualTo(TEST_SOL_INFO.atmosphericOpacity());
        assertThat(weather.uvIrradianceIndex()).isEqualTo(TEST_SOL_INFO.localUvIrradianceIndex());
    }

    @Test
    void getWeatherWithFutureDateThrowsInvalidDateException() {
        var futureDate = LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> weatherService.getWeather(futureDate))
                .isInstanceOf(InvalidDateException.class)
                .hasMessageContaining("date is after today");
    }

    @Test
    void getWeatherWithNoDataForSolThrowsNoWeatherForSolException() {
        var differentSolInfo = new SolInfo(
                1,
                LocalDate.of(2012, 8, 7),
                9999,
                150,
                "Month 6",
                null,
                null,
                null,
                "Lower",
                null,
                null,
                null,
                "Sunny",
                "05:30",
                "17:22",
                null,
                null,
                null);
        var nasaResponse = new NasaResponse(null, List.of(differentSolInfo));
        when(nasaClient.getMarsWeather()).thenReturn(nasaResponse);

        assertThatThrownBy(() -> weatherService.getWeather(TEST_DATE))
                .isInstanceOf(NoWeatherForSolException.class)
                .hasMessageContaining("no weather info for sol");
    }

    @Test
    void getWeatherWithNullTemperatures() {
        var solInfoWithNullTemps = new SolInfo(
                4551,
                LocalDate.of(2026, 2, 7),
                4802,
                221,
                "Month 8",
                null,
                null,
                807,
                "Higher",
                null,
                null,
                null,
                "Sunny",
                "05:23",
                "17:34",
                "Moderate",
                -83,
                12);
        var nasaResponse = new NasaResponse(null, List.of(solInfoWithNullTemps));
        when(nasaClient.getMarsWeather()).thenReturn(nasaResponse);

        var result = weatherService.getWeather(TEST_DATE);

        assertThat(result.weather().airTemperature()).isNull();
    }

    @Test
    void getWeatherWithNullGroundTemperatures() {
        var solInfoWithNullGroundTemps = new SolInfo(
                4551,
                LocalDate.of(2026, 2, 7),
                4802,
                221,
                "Month 8",
                -67,
                2,
                807,
                "Higher",
                null,
                null,
                null,
                "Sunny",
                "05:23",
                "17:34",
                "Moderate",
                null,
                null);
        var nasaResponse = new NasaResponse(null, List.of(solInfoWithNullGroundTemps));
        when(nasaClient.getMarsWeather()).thenReturn(nasaResponse);

        var result = weatherService.getWeather(TEST_DATE);

        assertThat(result.weather().groundTemperature()).isNull();
    }

    @Test
    void getWeatherWithNullPressure() {
        var solInfoWithNullPressure = new SolInfo(
                4551,
                LocalDate.of(2026, 2, 7),
                4802,
                221,
                "Month 8",
                -67,
                2,
                null,
                null,
                null,
                null,
                null,
                "Sunny",
                "05:23",
                "17:34",
                "Moderate",
                -83,
                12);
        var nasaResponse = new NasaResponse(null, List.of(solInfoWithNullPressure));
        when(nasaClient.getMarsWeather()).thenReturn(nasaResponse);

        var result = weatherService.getWeather(TEST_DATE);

        assertThat(result.weather().atmosphericPressure()).isNull();
    }

    @Test
    void getWeatherWithNullWind() {
        var solInfoWithNullWind = new SolInfo(
                4551,
                LocalDate.of(2026, 2, 7),
                4802,
                221,
                "Month 8",
                -67,
                2,
                807,
                "Higher",
                null,
                null,
                null,
                "Sunny",
                "05:23",
                "17:34",
                "Moderate",
                -83,
                12);
        var nasaResponse = new NasaResponse(null, List.of(solInfoWithNullWind));
        when(nasaClient.getMarsWeather()).thenReturn(nasaResponse);

        var result = weatherService.getWeather(TEST_DATE);

        assertThat(result.weather().wind()).isNull();
    }
}
