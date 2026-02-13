package net.laaber.marsweather.weather;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String date) {
        // check for parser errors and return an appropriate ResponseEntity
        var parsedDate = LocalDate.parse(date);

        var weatherResponse = weatherService.getWeather(parsedDate);

        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }
}
