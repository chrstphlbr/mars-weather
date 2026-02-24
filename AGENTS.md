# Mars Weather API - Agent Instructions

## Project Overview
Java Spring Boot 4.0.2 REST API providing Mars weather data from NASA's Curiosity rover.
- **Group**: `net.laaber`
- **Java**: 25 (with virtual threads enabled)
- **Build**: Gradle with Kotlin DSL
- **Package**: `net.laaber.marsweather`
- **Version**: `0.0.1-SNAPSHOT`

## Build Commands

```bash
# Build the project
./gradlew build

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "net.laaber.marsweather.ApplicationTests"

# Run a single test method
./gradlew test --tests "net.laaber.marsweather.ApplicationTests.contextLoads"

# Run with continuous build (auto-rebuild on changes)
./gradlew build --continuous

# Clean build artifacts
./gradlew clean

# Assemble JAR without running tests
./gradlew assemble

# Run the application locally
./gradlew bootRun

# Check for dependency updates
./gradlew dependencyUpdates
```

## Code Formatting (Spotless)

This project uses the **Palantir Java Format** via the Spotless Gradle plugin for consistent code formatting.

```bash
# Check if code is properly formatted
./gradlew spotlessCheck

# Apply formatting to fix any violations
./gradlew spotlessApply
```

## Code Style Guidelines

### General
This project uses the **Palantir Java Format** (enforced by Spotless Gradle plugin). Run `./gradlew spotlessApply` before committing to ensure consistent formatting.

- **Indentation**: 4 spaces (no tabs)
- **Line endings**: LF
- **Encoding**: UTF-8
- **Max line length**: 120 characters (soft limit)

### Naming Conventions
- **Classes**: PascalCase (e.g., `WeatherController`, `NasaClient`)
- **Methods**: camelCase (e.g., `getWeather()`, `from()`)
- **Variables**: camelCase (e.g., `parsedDate`, `weatherService`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `SECONDS_PER_SOL`)
- **Packages**: lowercase, reverse domain (e.g., `net.laaber.marsweather.weather`)

### Imports
- Organize imports: java.* first, then javax.*, then third-party, then project
- No wildcard imports (e.g., `import java.util.*`)
- Use static imports sparingly, only for constants in tests

### Types
- Use `var` for local variables when the type is obvious from the right-hand side
- Prefer Java Records for DTOs and immutable data carriers
- Make utility classes `final` with private constructor
- Use `Optional<T>` for method return types that may be empty
- Use constructor injection for dependencies (not field injection)

### Error Handling
- Use checked exceptions for recoverable errors
- Use unchecked exceptions (RuntimeException) for programming errors
- Provide meaningful error messages in exception constructors
- Use Spring's `@ControllerAdvice` for global exception handling
- Return appropriate HTTP status codes (e.g., 400 for bad input, 500 for server errors)

### Spring Boot Conventions
- Use constructor injection for dependencies
- Use `@RestController` for REST endpoints
- Use `@RequestMapping` with explicit paths
- Return `ResponseEntity<T>` for full control over HTTP responses
- Use constructor injection pattern shown in existing controllers

### Formatting
- Opening brace on same line: `public void method() {`
- One space after control flow keywords: `if (condition)`
- No spaces around method call parentheses: `method(arg)`
- One blank line between methods
- No trailing whitespace

### Documentation
- Javadoc for public APIs and complex logic
- Use `@param`, `@return`, `@throws` tags
- Keep comments current with code changes

## Testing Guidelines

### Test Structure
- Test class naming: `{ClassUnderTest}Tests` (e.g., `WeatherControllerTests`)
- Test method naming: descriptive camelCase or snake_case
- Use `@ExtendWith(SpringExtension.class)` for Spring tests
- Use `@WebMvcTest` for controller tests
- Use `@SpringBootTest` for integration tests

### Best Practices
- One assertion per test (where practical)
- Use `@MockBean` for mocking Spring components
- Test both happy path and edge cases
- Use descriptive test names that explain the behavior

## Docker Commands

```bash
# Build Docker image
./docker-build.sh

# Run Docker container
./docker-run.sh

# Manual Docker build
docker build -t marsweather:latest .

# Manual Docker run
docker run -p 8080:8080 marsweather:latest
```

## Architecture Notes
- Virtual threads enabled (Java 21+ feature)
- Graceful shutdown configured (30s timeout)
- NASA API client in `nasa` package
- Weather business logic in `weather` package
- Mars Sol date conversion in `sol` package
- Server port: 8080

## Completed Features
- `WeatherService.getWeather()` - implemented
- `WeatherResponse` record - fields defined
- `NasaResponse` record - fields defined
- Unit tests for `SolCalculator`, `NasaClient`, `WeatherService`, `WeatherController`
