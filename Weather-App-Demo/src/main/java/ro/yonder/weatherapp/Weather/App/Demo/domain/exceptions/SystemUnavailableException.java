package ro.yonder.weatherapp.Weather.App.Demo.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.ErrorMessageResponse;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class SystemUnavailableException extends RestClientException {

    private ErrorMessageResponse errorMessageResponse;

    public SystemUnavailableException(String msg) {
        super(msg);
    }

    public SystemUnavailableException(ErrorMessageResponse errorMessageResponse) {
        super(errorMessageResponse.getErrorMessage());
        this.errorMessageResponse = errorMessageResponse;

    }

    public ErrorMessageResponse getErrorMessageResponse() {
        return errorMessageResponse;
    }
}
