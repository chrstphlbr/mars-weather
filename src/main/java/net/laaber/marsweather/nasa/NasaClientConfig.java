package net.laaber.marsweather.nasa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class NasaClientConfig {

    private static final String URL = "https://mars.nasa.gov/rss/api";

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.baseUrl(URL).build();
    }
}
