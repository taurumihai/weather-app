package ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(
    {"name", "temperature", "wind"}
)
public class WeatherCityDto implements Comparable<WeatherCityDto>{

    @JsonIgnore
    private Long id;

    @JsonProperty("name")
    private String cityName;

    private String temperature;

    @JsonIgnore
    private String description;

    private String wind;

    @JsonIgnore
    Set<ForecastDto> forecast;

    @Override
    public int compareTo(WeatherCityDto secondDto) {
        return this.cityName.compareTo(secondDto.getCityName());
    }
}
