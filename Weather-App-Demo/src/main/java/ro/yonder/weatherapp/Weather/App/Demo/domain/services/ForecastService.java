package ro.yonder.weatherapp.Weather.App.Demo.domain.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.yonder.weatherapp.Weather.App.Demo.db.ForecastUseCase;
import ro.yonder.weatherapp.Weather.App.Demo.db.repo.ForecastRepository;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.Forecast;

@Slf4j
@Service
@AllArgsConstructor
public class ForecastService implements ForecastUseCase {

    private ForecastRepository forecastRepository;

    @Override
    public void saveForecast(Forecast forecast) {

        if (forecast != null){
            log.debug("Saving forecast to db...");
            forecastRepository.save(forecast);
        }

    }

    public void deleteAll() {
        log.debug("Deleting all records from db...");
        forecastRepository.deleteAll();;
    }
}
