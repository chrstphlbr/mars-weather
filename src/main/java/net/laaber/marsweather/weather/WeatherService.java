package net.laaber.marsweather.weather;

import java.time.LocalDate;
import net.laaber.marsweather.nasa.NasaClient;
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

        var nasaWeather = nasaClient.getMarsWeather();

        throw new RuntimeException("Not implemented");
    }
}
