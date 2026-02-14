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
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    private static final int SOLES_COUNT = 4551;

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

    private static void checkResponse(NasaResponse response) {
        assertThat(response.descriptions()).isNotNull();

        // check soles
        assertThat(response.soles()).describedAs("soles is null").isNotNull();

        var soles = response.soles();
        assertThat(soles.size()).describedAs("soles.size() incorrect").isEqualTo(SOLES_COUNT);

        var solesMap = soles.stream().collect(Collectors.toMap(SolInfo::sol, Function.identity()));
        assertThat(solesMap.size()).describedAs("solesMap.size() incorrect").isEqualTo(SOLES_COUNT);

        // check individual soles
        testData().forEach((var td) -> checkSol(solesMap, td.sol, td.expectedResult));
    }

    private static void checkSol(Map<Integer, SolInfo> soles, int sol, SolInfo expectedResult) {
        var solInfo = soles.get(sol);
        assertThat(solInfo).isNotNull();
        assertThat(solInfo.id()).isEqualTo(expectedResult.id());
        assertThat(solInfo.terrestrialDate()).isEqualTo(expectedResult.terrestrialDate());
        assertThat(solInfo.sol()).isEqualTo(sol);
        assertThat(solInfo.solarLongitude()).isEqualTo(expectedResult.solarLongitude());
        assertThat(solInfo.season()).isEqualTo(expectedResult.season());
        assertThat(solInfo.minAirTemperature()).isEqualTo(expectedResult.minAirTemperature());
        assertThat(solInfo.maxAirTemperature()).isEqualTo(expectedResult.maxAirTemperature());
        assertThat(solInfo.atmosphericPressure()).isEqualTo(expectedResult.atmosphericPressure());
        assertThat(solInfo.atmosphericPressureString()).isEqualTo(expectedResult.atmosphericPressureString());
        assertThat(solInfo.absoluteHumidity()).isEqualTo(expectedResult.absoluteHumidity());
        assertThat(solInfo.windSpeed()).isEqualTo(expectedResult.windSpeed());
        assertThat(solInfo.windDirection()).isEqualTo(expectedResult.windDirection());
        assertThat(solInfo.atmosphericOpacity()).isEqualTo(expectedResult.atmosphericOpacity());
        assertThat(solInfo.sunrise()).isEqualTo(expectedResult.sunrise());
        assertThat(solInfo.sunset()).isEqualTo(expectedResult.sunset());
        assertThat(solInfo.localUvIrradianceIndex()).isEqualTo(expectedResult.localUvIrradianceIndex());
        assertThat(solInfo.minGroundTemperature()).isEqualTo(expectedResult.minGroundTemperature());
        assertThat(solInfo.maxGroundTemperature()).isEqualTo(expectedResult.maxGroundTemperature());
    }

    private static Stream<TestData> testData() {
        return Stream.of(
                new TestData(
                        1,
                        new SolInfo(
                                1,
                                LocalDate.of(2012, 8, 7),
                                1,
                                150,
                                "Month 6",
                                null,
                                null,
                                null,
                                "Lower",
                                null,
                                null,
                                null,
                                "Sunny",
                                "05:30",
                                "17:22",
                                null,
                                null,
                                null)),
                new TestData(
                        4801,
                        new SolInfo(
                                4551,
                                LocalDate.of(2026, 2, 7),
                                4801,
                                221,
                                "Month 8",
                                -67,
                                2,
                                807,
                                "Higher",
                                null,
                                null,
                                null,
                                "Sunny",
                                "05:23",
                                "17:34",
                                "Moderate",
                                -83,
                                12)));
    }

    private record TestData(int sol, SolInfo expectedResult) {}
}
