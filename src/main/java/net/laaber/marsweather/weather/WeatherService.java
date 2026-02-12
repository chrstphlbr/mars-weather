package net.laaber.marsweather.weather;

import net.laaber.marsweather.nasa.NasaClient;
import net.laaber.marsweather.sol.Sol;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeatherService {

    private final NasaClient nasaClient;

    public WeatherService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
    }

    public WeatherResponse getWeather(LocalDate date) {
        var sol = Sol.from(date);

        var nasaWeather = nasaClient.getWeather();

        throw new RuntimeException("Not implemented");
    }

}
