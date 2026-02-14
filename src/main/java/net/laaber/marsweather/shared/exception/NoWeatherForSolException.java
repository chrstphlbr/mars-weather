package net.laaber.marsweather.shared.exception;

public class NoWeatherForSolException extends RuntimeException {
    public NoWeatherForSolException(String message) {
        super(message);
    }
}
