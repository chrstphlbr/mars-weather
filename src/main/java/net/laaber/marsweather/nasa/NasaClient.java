package net.laaber.marsweather.nasa;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NasaClient {

    private static final String URL = "https://mars.nasa.gov/rss/api/?feed=weather&feedtype=json&ver=1.0&category=msl";
    private final RestClient restClient;

    public NasaClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public NasaResponse getWeather() {
        return restClient.get()
                .uri(URL)
                .retrieve()
                .body(NasaResponse.class);
    }
}
