package ro.yonder.weatherapp.Weather.App.Demo.domain.model;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ro.yonder.weatherapp.Weather.App.Demo.db.ModelMapper;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.ForecastDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.WeatherCityDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.Forecast;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.WeatherCity;

import java.util.HashSet;
import java.util.Set;

@Component
@NoArgsConstructor
public class CustomModelMapper implements ModelMapper {


    @Override
    public Forecast toEntity(ForecastDto forecastDto) {
        return Forecast.builder()
                .id(forecastDto.getId())
                .temperature(forecastDto.getTemperature())
                .day(forecastDto.getDay())
                .wind(forecastDto.getWind())
                .build();
    }

    @Override
    public ForecastDto toDto(Forecast forecast) {
        return ForecastDto.builder()
                .id(forecast.getId())
                .day(forecast.getDay())
                .temperature(forecast.getTemperature())
                .wind(forecast.getWind())
                .weatherCityId(forecast.getWeatherCityInfo().getId())
                .build();
    }

    @Override
    public WeatherCity toEntity(WeatherCityDto weatherCityDto) {

        Set<Forecast> forecastSet = new HashSet<>();
        if (!weatherCityDto.getForecast().isEmpty()) {
            for (ForecastDto forecastDto : weatherCityDto.getForecast()) {
                forecastSet.add(toEntity(forecastDto));
            }
        }

        return WeatherCity.builder()
                .id(weatherCityDto.getId())
                .cityName(weatherCityDto.getCityName())
                .description(weatherCityDto.getDescription())
                .temperature(weatherCityDto.getTemperature())
                .wind(weatherCityDto.getWind())
                .forecast(forecastSet)
                .build();
    }

    @Override
    public WeatherCityDto toDto(WeatherCity weatherCity) {

        Set<ForecastDto> forecastDto = new HashSet<>();
        for (Forecast forecast : weatherCity.getForecast()) {
            forecastDto.add(toDto(forecast));
        }

        return WeatherCityDto.builder()
                .id(weatherCity.getId())
                .temperature(weatherCity.getTemperature())
                .wind(weatherCity.getWind())
                .cityName(weatherCity.getCityName())
                .forecast(forecastDto)
                .build();
    }
}
