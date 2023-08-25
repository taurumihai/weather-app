package ro.yonder.weatherapp.Weather.App.Demo.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.Forecast;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
}
