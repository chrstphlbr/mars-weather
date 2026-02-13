package net.laaber.marsweather.nasa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NasaClient {

    private final RestClient restClient;

    public NasaClient(RestClient.Builder restClientBuilder, @Value("${nasa.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public NasaResponse getMarsWeather() {
        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("feed", "weather")
                        .queryParam("feedtype", "json")
                        .queryParam("ver", "1.0")
                        .queryParam("category", "msl")
                        .build())
                .retrieve()
                .body(NasaResponse.class);
    }
}
