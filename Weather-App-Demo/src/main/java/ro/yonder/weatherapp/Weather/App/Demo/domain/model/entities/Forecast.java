package ro.yonder.weatherapp.Weather.App.Demo.domain.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "forecast_table")
public class Forecast implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "wind")
    private String wind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_city_id")
    private WeatherCity weatherCityInfo;
}
