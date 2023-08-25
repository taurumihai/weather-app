package ro.yonder.weatherapp.Weather.App.Demo.db;

import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.Forecast;

public interface ForecastUseCase {

    void saveForecast(Forecast forecast);
}
