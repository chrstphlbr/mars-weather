# Mars Weather API

A RESTful API providing Mars weather data from NASA's Curiosity rover.

## Overview

This Spring Boot application fetches and serves weather data from NASA's Mars Curiosity rover. The API converts Earth dates to Mars Sol dates and returns temperature, pressure, wind, and atmospheric conditions.

## Getting Started

### Prerequisites

On the host:
- **Java 25** (with virtual threads support, e.g., [Adoptium / Eclipse Temurin](https://adoptium.net/temurin/releases?version=25&os=any&arch=any); tested with 25.0.2)
- **Gradle** (wrapper included, does not need extra installation; tested with 9.3.1)

In a Docker container:
- **Docker** (tested with 29.2.0)

### Build and Run (on the host)

```bash
# Build the project
./gradlew build

# Run locally
./gradlew bootRun

# Or build and run the JAR
./gradlew build
java -jar build/libs/marsweather-0.0.1-SNAPSHOT.jar
```

The API will be available at `http://localhost:8080`

### Docker

```bash
# Build Docker image
./docker-build.sh

# Run container
./docker-run.sh

# Or manually
docker build -t marsweather:latest .
docker run -p 8080:8080 marsweather:latest
```

The API will be available at `http://localhost:8080`

## API Usage

### Get Weather by Date

Retrieves Mars weather data for a specific Earth date.

**Endpoint:**

```
GET /weather?date={yyyy-MM-dd}
```

**Parameters:**

| Parameter | Type   | Required | Description                              |
|-----------|--------|----------|------------------------------------------|
| date      | Date   | Yes      | Earth date in ISO format (yyyy-MM-dd)    |

**Example Request:**

```bash
curl "http://localhost:8080/weather?date=2026-02-10"
```

**Example Response:**

```json
{
  "earthDate": "2026-02-10",
  "sol": 4804,
  "weather": {
    "airTemperature": {
      "min": "-66",
      "max": "2"
    },
    "groundTemperature": {
      "min": "-80",
      "max": "11"
    },
    "atmosphericPressure": {
      "value": "810",
      "description": "Higher"
    },
    "atmosphericOpacity": "Sunny",
    "uvIrradianceIndex": "Moderate",
    "wind": {
      "speed": "--",
      "direction":"--"
    },
    "absoluteHumidity":"--"
  }
}
```

### Error Responses

The API uses RFC 7807 Problem Details for error responses.

**400 Bad Request - Invalid Date (before the Curiosity landing on 2012-08-06):**

```json
{
  "instance": "/weather",
  "status": 400,
  "title": "Invalid date",
  "detail": "date is before the Curiosity landing"
}
```

**400 Bad Request - Invalid Date (after today):**

```json
{
  "instance": "/weather",
  "status": 400,
  "title": "Invalid date",
  "detail": "date is after today"
}
```

**404 Not Found - No Weather Data:**

```json
{
  "instance": "/weather",
  "status": 404,
  "title": "No weather info",
  "detail": "no weather info for sol 4808"
}
```

**500 Internal Server Error:**

```json
{
  "instance":"/weather",
  "status": 500,
  "title": "An unexpected error occurred",
  "detail": "Error message"
}
```

## Data Sources

Weather data is sourced from NASA's Mars Curiosity rover through the [NASA Mars API]( https://mars.nasa.gov/rss/api/?feed=weather&feedtype=json&ver=1.0&category=msl).

## Development

### Project Structure

```
src/
├── main/java/net/laaber/marsweather/
│   ├── weather/          # Weather API endpoint and business logic
│   ├── nasa/             # NASA API client
│   ├── shared/           # Utilities and exception handling
│   └── Application.java  # Spring Boot entry point
├── test/java/            # Unit and integration tests
└── main/resources/       # Application configuration
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "net.laaber.marsweather.nasa.NasaClientTest"

# Run with continuous testing
./gradlew test --continuous
```

### Code Style

This project uses Spotless with Palantir Java Format:

```bash
# Check formatting
./gradlew spotlessCheck

# Apply formatting
./gradlew spotlessApply
```

## Configuration

| Property                      | Default                          | Description               |
|-------------------------------|----------------------------------|---------------------------|
| `server.port`                 | 8080                             | HTTP server port          |

Configuration can be overridden via environment variables or `application.yaml`.

## License

This project is licensed under the MIT License; see the [LICENSE](LICENSE) file for details.
