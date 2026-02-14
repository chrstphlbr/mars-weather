package net.laaber.marsweather.nasa;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    private static int SOLES_COUNT = 4551;

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

        checkResponse(response);

        // Verify the call was made
        verify(getRequestedFor(urlPathEqualTo("/"))
                .withQueryParam("feed", equalTo("weather"))
                .withQueryParam("feedtype", equalTo("json"))
                .withQueryParam("ver", equalTo("1.0"))
                .withQueryParam("category", equalTo("msl")));
    }

    private void checkResponse(NasaResponse response) {
        assertThat(response.descriptions()).isNotNull();

        // check soles
        assertThat(response.soles()).describedAs("soles is null").isNotNull();

        var soles = response.soles();
        assertThat(soles.size()).describedAs("soles.size() incorrect").isEqualTo(SOLES_COUNT);

        var solesMap = soles.stream().collect(Collectors.toMap(SolInfo::sol, Function.identity()));
        assertThat(solesMap.size()).describedAs("solesMap.size() incorrect").isEqualTo(SOLES_COUNT);

        // check individual soles
        checkSol1(solesMap);
        checkSol4801(solesMap);
    }

    private void checkSol1(Map<Integer, SolInfo> soles) {
        var sol = 1;
        var solInfo = soles.get(sol);
        assertThat(solInfo).isNotNull();
        assertThat(solInfo.id()).isEqualTo(1);
        assertThat(solInfo.terrestrialDate()).isEqualTo("2012-08-07");
        assertThat(solInfo.sol()).isEqualTo(sol);
        assertThat(solInfo.solarLongitude()).isEqualTo(150);
        assertThat(solInfo.season()).isEqualTo("Month 6");
        assertThat(solInfo.minAirTemperature()).isEqualTo(null);
        assertThat(solInfo.maxAirTemperature()).isEqualTo(null);
        assertThat(solInfo.atmosphericPressure()).isEqualTo(null);
        assertThat(solInfo.atmosphericPressureString()).isEqualTo("Lower");
        assertThat(solInfo.absoluteHumidity()).isEqualTo(null);
        assertThat(solInfo.windSpeed()).isEqualTo(null);
        assertThat(solInfo.windDirection()).isEqualTo(null);
        assertThat(solInfo.atmosphericOpacity()).isEqualTo("Sunny");
        assertThat(solInfo.sunrise()).isEqualTo("05:30");
        assertThat(solInfo.sunset()).isEqualTo("17:22");
        assertThat(solInfo.localUvIrradianceIndex()).isEqualTo(null);
        assertThat(solInfo.minGroundTemperature()).isEqualTo(null);
        assertThat(solInfo.maxGroundTemperature()).isEqualTo(null);
    }

    private void checkSol4801(Map<Integer, SolInfo> soles) {
        var sol = 4801;
        var solInfo = soles.get(sol);
        assertThat(solInfo).isNotNull();
        assertThat(solInfo.id()).isEqualTo(4551);
        assertThat(solInfo.terrestrialDate()).isEqualTo("2026-02-07");
        assertThat(solInfo.sol()).isEqualTo(sol);
        assertThat(solInfo.solarLongitude()).isEqualTo(221);
        assertThat(solInfo.season()).isEqualTo("Month 8");
        assertThat(solInfo.minAirTemperature()).isEqualTo(-67);
        assertThat(solInfo.maxAirTemperature()).isEqualTo(2);
        assertThat(solInfo.atmosphericPressure()).isEqualTo(807);
        assertThat(solInfo.atmosphericPressureString()).isEqualTo("Higher");
        assertThat(solInfo.absoluteHumidity()).isEqualTo(null);
        assertThat(solInfo.windSpeed()).isEqualTo(null);
        assertThat(solInfo.windDirection()).isEqualTo(null);
        assertThat(solInfo.atmosphericOpacity()).isEqualTo("Sunny");
        assertThat(solInfo.sunrise()).isEqualTo("05:23");
        assertThat(solInfo.sunset()).isEqualTo("17:34");
        assertThat(solInfo.localUvIrradianceIndex()).isEqualTo("Moderate");
        assertThat(solInfo.minGroundTemperature()).isEqualTo(-83);
        assertThat(solInfo.maxGroundTemperature()).isEqualTo(12);
    }
}
