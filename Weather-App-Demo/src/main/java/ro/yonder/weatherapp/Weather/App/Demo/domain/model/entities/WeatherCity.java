package ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@Table(name = "weather")
@AllArgsConstructor
@NoArgsConstructor
public class WeatherCity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "description")
    private String description;

    @Column(name = "wind")
    private String wind;

    @OneToMany(mappedBy = "weatherCityInfo")
    private Set<Forecast> forecast;

}
