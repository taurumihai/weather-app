package ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDto {

    private Long id;
    private String day;
    private String temperature;
    private String wind;
    private Long weatherCityId;
}
