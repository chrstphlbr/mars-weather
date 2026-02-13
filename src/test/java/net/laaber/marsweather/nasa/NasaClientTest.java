package net.laaber.marsweather.nasa;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NasaClientTest {

    private WireMockServer wireMockServer;

    @Autowired
    private NasaClient nasaClient;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("nasa.base-url", () -> "http://localhost:8089");
    }

    @BeforeAll
    void setupWireMock() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    @AfterAll
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void retrieveMarsWeather() {
        // Setup WireMock stub
        stubFor(get(urlPathEqualTo("/"))
                .withQueryParam("feed", equalTo("weather"))
                .withQueryParam("feedtype", equalTo("json"))
                .withQueryParam("ver", equalTo("1.0"))
                .withQueryParam("category", equalTo("msl"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("mars-nasa-gov-2026-02-11.json")));

        // Test NasaClient
        var response = nasaClient.getMarsWeather();
        assertThat(response).isNotNull();

        // Verify the call was made
        verify(getRequestedFor(urlPathEqualTo("/"))
                .withQueryParam("feed", equalTo("weather"))
                .withQueryParam("feedtype", equalTo("json"))
                .withQueryParam("ver", equalTo("1.0"))
                .withQueryParam("category", equalTo("msl")));
    }
}
