// package net.laaber.marsweather.shared.config;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.time.LocalDate;
// import net.laaber.marsweather.nasa.SolInfo;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// @SpringBootTest
// class JacksonConfigTest {
//
//    @Autowired
//    public ObjectMapper objectMapper;
//
//    //    @BeforeEach
//    //    void setUp() {
//    //        objectMapper = new ObjectMapper();
//    //        objectMapper.registerModule(new Jdk8Module());
//    //        objectMapper.registerModule(new JavaTimeModule());
//    //        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//    //    }
//
//    // ========== Integer Tests ==========
//
//    @Test
//    void testIntegerDashDeserializesToEmpty() throws Exception {
//        String json = """
//                {
//                    "pressure": "--"
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.pressure().isEmpty(), "Pressure should be empty for '--'");
//    }
//
//    @Test
//    void testIntegerNullDeserializesToEmpty() throws Exception {
//        String json = """
//                {
//                    "pressure": null
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.pressure().isEmpty(), "Pressure should be empty for null");
//    }
//
//    @Test
//    void testIntegerValidNumberDeserializesSuccessfully() throws Exception {
//        String json = """
//                {
//                    "pressure": 750
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.pressure().isPresent(), "Pressure should be present");
//        assertEquals(750, solInfo.pressure().get(), "Pressure should be 750");
//    }
//
//    @Test
//    void testIntegerStringNumberConvertsSuccessfully() throws Exception {
//        String json = """
//                {
//                    "pressure": "750"
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.pressure().isPresent(), "Pressure should be present");
//        assertEquals(750, solInfo.pressure().get(), "Pressure should be 750 from string");
//    }
//
//    @Test
//    void testIntegerInvalidStringThrowsException() {
//        String json = """
//                {
//                    "pressure": "invalid"
//                }
//                """;
//
//        assertThrows(
//                Exception.class,
//                () -> {
//                    objectMapper.readValue(json, SolInfo.class);
//                },
//                "Should throw exception for invalid integer string");
//    }
//
//    // ========== String Tests ==========
//
//    @Test
//    void testStringDashDeserializesToEmpty() throws Exception {
//        String json = """
//                {
//                    "windDirection": "--"
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.windDirection().isEmpty(), "WindDirection should be empty for '--'");
//    }
//
//    @Test
//    void testStringNullDeserializesToEmpty() throws Exception {
//        String json = """
//                {
//                    "windDirection": null
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.windDirection().isEmpty(), "WindDirection should be empty for null");
//    }
//
//    @Test
//    void testStringValidValueDeserializesSuccessfully() throws Exception {
//        String json = """
//                {
//                    "windDirection": "NNE"
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.windDirection().isPresent(), "WindDirection should be present");
//        assertEquals("NNE", solInfo.windDirection().get(), "WindDirection should be 'NNE'");
//    }
//
//    @Test
//    void testStringEmptyStringDeserializesSuccessfully() throws Exception {
//        String json = """
//                {
//                    "windDirection": ""
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.windDirection().isPresent(), "WindDirection should be present for empty string");
//        assertEquals("", solInfo.windDirection().get(), "WindDirection should be empty string");
//    }
//
//    // ========== LocalDate Tests ==========
//
//    @Test
//    void testLocalDateValidDateDeserializesSuccessfully() throws Exception {
//        String json = """
//                {
//                    "terrestrialDate": "2024-02-13"
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertEquals(LocalDate.of(2024, 2, 13), solInfo.terrestrialDate(), "TerrestrialDate should be 2024-02-13");
//    }
//
//    @Test
//    void testLocalDateInvalidFormatThrowsException() {
//        String json = """
//                {
//                    "terrestrialDate": "13/02/2024"
//                }
//                """;
//
//        assertThrows(
//                Exception.class,
//                () -> {
//                    objectMapper.readValue(json, SolInfo.class);
//                },
//                "Should throw exception for invalid date format");
//    }
//
//    @Test
//    void testLocalDateInvalidStringThrowsException() {
//        String json = """
//                {
//                    "terrestrialDate": "invalid-date"
//                }
//                """;
//
//        assertThrows(
//                Exception.class,
//                () -> {
//                    objectMapper.readValue(json, SolInfo.class);
//                },
//                "Should throw exception for invalid date string");
//    }
//
//    // ========== Combined Test ==========
//
//    @Test
//    void testMultipleFieldsWithMixedValues() throws Exception {
//        String json = """
//                {
//                    "pressure": 750,
//                    "windDirection": "--",
//                    "terrestrialDate": "2024-02-13"
//                }
//                """;
//
//        SolInfo solInfo = objectMapper.readValue(json, SolInfo.class);
//
//        assertTrue(solInfo.pressure().isPresent(), "Pressure should be present");
//        assertEquals(750, solInfo.pressure().get(), "Pressure should be 750");
//
//        assertTrue(solInfo.windDirection().isEmpty(), "WindDirection should be empty for '--'");
//
//        assertEquals(LocalDate.of(2024, 2, 13), solInfo.terrestrialDate());
//    }
// }
