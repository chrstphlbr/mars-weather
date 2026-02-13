package net.laaber.marsweather.weather;

import java.time.LocalDate;
import java.util.Optional;
import net.laaber.marsweather.nasa.NasaClient;
import net.laaber.marsweather.nasa.NasaResponse;
import net.laaber.marsweather.nasa.SolInfo;
import net.laaber.marsweather.sol.Sol;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final NasaClient nasaClient;

    public WeatherService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
    }

    public WeatherResponse getWeather(LocalDate date) {
        var sol = Sol.from(date);

        var nasaResponse = nasaClient.getMarsWeather();

        var maybeSolInfo = solInfo(nasaResponse, sol);
        if (maybeSolInfo.isEmpty()) {
            throw new IllegalArgumentException("no info for sol %d".formatted(sol));
        }

        return weatherResponse(date, sol, maybeSolInfo.get());
    }

    private Optional<SolInfo> solInfo(NasaResponse nasaResponse, int sol) {
        return nasaResponse.soles().stream()
                .filter((var solInfo) -> solInfo.sol() == sol)
                .findFirst();
    }

    private WeatherResponse weatherResponse(LocalDate date, int sol, SolInfo solInfo) {
        return new WeatherResponse(
                date,
                sol,
                new WeatherInfo(
                        Optional.of(new Temperature(solInfo.minTemp(), solInfo.maxTemp())),
                        Optional.of(new Temperature(solInfo.minGtsTemp(), solInfo.maxGtsTemp())),
                        Optional.of(new Pressure(solInfo.pressure(), solInfo.pressureString())),
                        Optional.of(solInfo.atmoOpacity()),
                        Optional.of(solInfo.localUvIrradianceIndex()),
                        Optional.of(new Wind(solInfo.windSpeed(), solInfo.windDirection())),
                        solInfo.absHumidity()));
    }
}
