package ro.yonder.weatherapp.Weather.App.Demo.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities.WeatherCity;

@Repository
public interface WeatherCityRepository extends JpaRepository<WeatherCity, Long> {
}
