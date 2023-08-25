package ro.yonder.weatherapp.Weather.App.Demo.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.yonder.weatherapp.Weather.App.Demo.domain.exceptions.CityNotFoundException;
import ro.yonder.weatherapp.Weather.App.Demo.domain.exceptions.SystemUnavailableException;

@ControllerAdvice
public class WeatherExceptionHandlerController {

    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<?> cityNotFoundException(CityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorMessageResponse().getErrorMessage());
    }

    @ExceptionHandler(value = SystemUnavailableException.class)
    public ResponseEntity<?> systemUnavailableException(SystemUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getErrorMessageResponse().getErrorMessage());
    }
}
