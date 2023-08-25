package ro.yonder.weatherapp.Weather.App.Demo.domain.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.yonder.weatherapp.Weather.App.Demo.db.repo.WeatherCityRepository;
import ro.yonder.weatherapp.Weather.App.Demo.db.WeatherCityUseCase;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.CustomModelMapper;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.ForecastDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.WeatherCityDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.WeatherCity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherCityService implements WeatherCityUseCase {

    private WeatherCityRepository weatherCityRepository;
    private CustomModelMapper customModelMapper;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public WeatherCity saveWeatherCity(WeatherCity weatherCity) {
        log.debug("Saving new weatherCity entity : {}", weatherCity);
        if (weatherCity != null) {
             return weatherCityRepository.save(weatherCity);
        }

        log.warn("Parsed weatherCity entity is null");
        return null;
    }

    /*
    * Not using customModelMapper in lambda expression below because we lose performance when sent the response with ~3s
    * CustomModelMapper needs to be refactored to handle better performance
    * */

    public List<WeatherCityDto> getRequestedCitiesWeatherInfo() {

        log.debug("Getting requested city weather info..");
        List<WeatherCityDto> requestedCitiesInfo = weatherCityRepository.findAll().stream()
                .map(weatherCity -> {
                    Set<ForecastDto> forecastDtoSet = weatherCity.getForecast()
                            .stream()
                            .map(forecast -> new ForecastDto(forecast.getId(), forecast.getDay(), forecast.getTemperature(), forecast.getWind(), weatherCity.getId()))
                            .collect(Collectors.toSet());
                    return new WeatherCityDto(weatherCity.getId(), weatherCity.getCityName(), weatherCity.getTemperature(), weatherCity.getDescription(), weatherCity.getWind(), forecastDtoSet);
                })
                .collect(Collectors.toList());

        for (WeatherCityDto weatherCityDto : requestedCitiesInfo) {

            double averageTemperature = 0.0;
            double averageWind = 0.0;

            for (ForecastDto forecast : weatherCityDto.getForecast()) {
                forecast.setTemperature(forecast.getTemperature().replaceAll("[^0-9]", ""));
                forecast.setWind(forecast.getWind().replaceAll("[^0-9]", ""));


                if (!forecast.getTemperature().isEmpty()) {
                    averageTemperature = averageTemperature + Double.parseDouble(forecast.getTemperature());
                }
                if (!forecast.getWind().isEmpty()) {
                    averageWind = averageWind + Double.parseDouble(forecast.getWind());
                }
            }

            df.setRoundingMode(RoundingMode.UP);

            averageWind = averageWind / weatherCityDto.getForecast().size();
            averageTemperature = averageTemperature / weatherCityDto.getForecast().size();

            weatherCityDto.setTemperature(df.format(averageTemperature) + " ℃") ;
            weatherCityDto.setWind(df.format(averageWind) + " km/h");

            saveWeatherCity(customModelMapper.toEntity(weatherCityDto));

            log.debug("Logging requested city info..." + weatherCityDto);
        }

        return requestedCitiesInfo;
    }

    public void deleteAll() {
        log.debug("Deleting all entities from DB....");
        weatherCityRepository.deleteAll();;
    }
}
