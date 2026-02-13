package net.laaber.marsweather.nasa;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NasaClient {

    private final RestClient restClient;

    public NasaClient(RestClient restClient) {
        this.restClient =restClient;
    }

    public NasaResponse getMarsWeather() {
        return restClient.get()
                .uri("/?feed=weather&feedtype=json&ver=1.0&category=msl")
                .retrieve()
                .body(NasaResponse.class);
    }
}
