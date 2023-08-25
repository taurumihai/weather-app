package ro.yonder.weatherapp.Weather.App.Demo.db;

import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.WeatherCity;

public interface WeatherCityUseCase {

    WeatherCity saveWeatherCity(WeatherCity weatherCity);
}
