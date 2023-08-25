package ro.yonder.weatherapp.Weather.App.Demo.domain.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.yonder.weatherapp.Weather.App.Demo.domain.model.dto.WeatherCityDto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CsvFileService {

    public void generateCsvFile(List<WeatherCityDto> weatherCityDto) {
        log.info("Initializing csv file generation...");
        File file = new File("C:\\test\\weather.csv");
        file.getParentFile().mkdirs();


        try (FileWriter writer = new FileWriter(file)) {

            /*
            * Setting headers for csv file
            * */
            writer.write("Name,Temperature,Wind\n");
            log.debug("CSV header setted...");
            for (WeatherCityDto weatherCity : weatherCityDto) {
                writer.write(
                        weatherCity.getCityName() + "," +
                                weatherCity.getTemperature() + "," +
                                weatherCity.getWind() + "\n"
                );

            }
            log.debug("File generation successfully.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("File saved into C:\\test\\weather.csv");
    }
}
