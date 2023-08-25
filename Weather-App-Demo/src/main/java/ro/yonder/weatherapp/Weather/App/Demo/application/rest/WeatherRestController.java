package ro.yonder.weatherapp.Weather.App.Demo.application.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import ro.yonder.weatherapp.Weather.App.Demo.domain.exceptions.CityNotFoundException;
import ro.yonder.weatherapp.Weather.App.Demo.domain.exceptions.SystemUnavailableException;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.CustomModelMapper;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.ErrorMessageResponse;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.WeatherCityDto;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.Forecast;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.WeatherCity;
import ro.yonder.weatherapp.Weather.App.Demo.domain.services.CsvFileService;
import ro.yonder.weatherapp.Weather.App.Demo.domain.services.ForecastService;
import ro.yonder.weatherapp.Weather.App.Demo.domain.services.WeatherCityService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static ro.yonder.weatherapp.Weather.App.Demo.application.YonderWeatherConstants.*;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WeatherRestController {

    private WeatherCityService weatherCityService;
    private ForecastService forecastService;
    private CustomModelMapper customModelMapper;
    private CsvFileService csvFileService;


    @GetMapping("/weather")
    public ResponseEntity<?> getWeatherByCity(@RequestParam List<String> city) throws RestClientException {

        for(String s : city) {
            log.info("Receive the following cities from request: {} ", s);
        }

        forecastService.deleteAll();
        weatherCityService.deleteAll();

        Gson gson = new Gson();
        for (String cityString : city) {

            final String fullUrlApi = WEATHER_API_URL + cityString;
            RestTemplate restTemplate = new RestTemplate();
            String result;
            WeatherCity savedCity;

            try {
                result = restTemplate.getForObject(fullUrlApi, String.class);
                log.debug("Result string from API {} : ", result);

                /*
                * checking if city provided in request actually exists, if not, throw error and a proper message
                * we can check temperature, description or wind, for forecast, it will return a list with one element, which is empty as well
                * if one of the fields is empty, then the city does not exist
                * */
                savedCity = customModelMapper.toEntity(gson.fromJson(result, WeatherCityDto.class));
                if (savedCity.getTemperature().isEmpty()) {
                    log.error("An error occurred, city received as parameter is not a valid input");
                    throw new CityNotFoundException();
                }

            } catch (RestClientException | CityNotFoundException e) {

                /*
                * Thrown only when API is not available, from time to time API is not working for around 5-10 mins
                * */
                if (e instanceof RestClientException) {
                    log.error("System is unavailable at this moment...");
                    throw new SystemUnavailableException(new ErrorMessageResponse(SYSTEM_UNAVAILABLE));
                }

                log.error("No city found!");
                throw new CityNotFoundException(new ErrorMessageResponse(CITY_NOT_FOUND + cityString));
            }

            savedCity = weatherCityService.saveWeatherCity(customModelMapper.toEntity(gson.fromJson(result, WeatherCityDto.class)));
            savedCity.setCityName(cityString);

            Set<Forecast> forecastDtoSet = savedCity.getForecast();
            WeatherCity finalSavedCity = savedCity;
            forecastDtoSet.forEach(forecast -> {
                forecast.setWeatherCityInfo(finalSavedCity);
                forecastService.saveForecast(forecast);
            });
        }

        List<WeatherCityDto> finalWeatherList = weatherCityService.getRequestedCitiesWeatherInfo();
        log.debug("Sorting final result list...");
        Collections.sort(finalWeatherList);

        csvFileService.generateCsvFile(finalWeatherList);

        return new ResponseEntity<>(finalWeatherList, HttpStatus.OK);
    }
}
