package ro.yonder.weatherapp.Weather.App.Demo.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.ErrorMessageResponse;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends RuntimeException{

    private ErrorMessageResponse message;

    public CityNotFoundException(ErrorMessageResponse message) {
        this.message = message;
    }

    public CityNotFoundException() {

    }

    public ErrorMessageResponse getErrorMessageResponse() {
        return message;
    }

}
