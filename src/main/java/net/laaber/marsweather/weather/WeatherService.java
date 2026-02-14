package net.laaber.marsweather.weather;

import java.time.LocalDate;
import java.util.Optional;
import net.laaber.marsweather.nasa.NasaClient;
import net.laaber.marsweather.nasa.NasaResponse;
import net.laaber.marsweather.nasa.SolInfo;
import net.laaber.marsweather.shared.exception.InvalidDateException;
import net.laaber.marsweather.shared.exception.NoWeatherForSolException;
import net.laaber.marsweather.shared.util.SolCalculator;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final NasaClient nasaClient;

    public WeatherService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
    }

    public WeatherResponse getWeather(LocalDate date) {
        // validate not after today
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("date is after today");
        }

        var sol = SolCalculator.from(date);

        var nasaResponse = nasaClient.getMarsWeather();

        var maybeSolInfo = solInfo(nasaResponse, sol);
        if (maybeSolInfo.isEmpty()) {
            throw new NoWeatherForSolException("no weather info for sol %d".formatted(sol));
        }

        return weatherResponse(date, sol, maybeSolInfo.get());
    }

    private Optional<SolInfo> solInfo(NasaResponse nasaResponse, int sol) {
        return nasaResponse.soles().stream()
                .filter((var solInfo) -> Integer.parseInt(solInfo.sol()) == sol)
                .findFirst();
    }

    private WeatherResponse weatherResponse(LocalDate date, int sol, SolInfo solInfo) {
        return new WeatherResponse(
                date,
                sol,
                new WeatherInfo(
                        new Temperature(solInfo.minAirTemperature(), solInfo.maxAirTemperature()),
                        new Temperature(solInfo.minGroundTemperature(), solInfo.maxGroundTemperature()),
                        new Pressure(solInfo.atmosphericPressure(), solInfo.pressureString()),
                        solInfo.atmosphericOpacity(),
                        solInfo.localUvIrradianceIndex(),
                        new Wind(solInfo.windSpeed(), solInfo.windDirection()),
                        solInfo.absoluteHumidity()));
    }
}
