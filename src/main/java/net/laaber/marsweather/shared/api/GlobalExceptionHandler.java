package net.laaber.marsweather.shared.api;

import net.laaber.marsweather.shared.exception.InvalidDateException;
import net.laaber.marsweather.shared.exception.NoWeatherForSolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateException.class)
    public ProblemDetail handleInvalidDate(InvalidDateException ex) {
        var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid date");
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(NoWeatherForSolException.class)
    public ProblemDetail handleInvalidDate(NoWeatherForSolException ex) {
        var problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("No weather info");
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex) {
        var problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("An unexpected error occurred");
        problem.setDetail(ex.getMessage());
        return problem;
    }
}
