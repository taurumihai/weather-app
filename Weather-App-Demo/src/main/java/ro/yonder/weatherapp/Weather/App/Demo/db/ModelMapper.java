package ro.yonder.weatherapp.Weather.App.Demo.db;

import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.ForecastDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.WeatherCityDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.Forecast;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.WeatherCity;

public interface ModelMapper {

    Forecast toEntity(ForecastDto forecastDto);

    ForecastDto toDto(Forecast forecast);

    WeatherCity toEntity(WeatherCityDto weatherCityDto);

    WeatherCityDto toDto(WeatherCity weatherCity);
}
