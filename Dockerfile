# ---------- Build stage ----------
FROM eclipse-temurin:25-jdk AS build

WORKDIR /workspace

# Copy Gradle wrapper & build files first (layer caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# Download dependencies
RUN ./gradlew --no-daemon dependencies || true

# Copy source last to leverage Docker cache
COPY src src

# Build fat jar
RUN ./gradlew --no-daemon clean bootJar


# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jre

# Install tini for signal forwarding
RUN apt-get update && apt-get install -y tini && rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN useradd -r -u 1001 appuser

WORKDIR /app

# Copy fat jar from build stage
COPY --from=build /workspace/build/libs/*.jar app.jar

# Copy entrypoint script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Set ownership to non-root
RUN chown appuser:appuser /app/app.jar /app/entrypoint.sh

# Switch to non-root user
USER appuser

# Expose HTTP port
EXPOSE 8080

# Default JVM options (override at runtime with -e JAVA_OPTS="...")
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseZGC -Dspring.lifecycle.timeout-per-shutdown-phase=30s"

# Use tini to forward signals and start the app via entrypoint.sh
ENTRYPOINT ["/usr/bin/tini", "--", "/app/entrypoint.sh"]
