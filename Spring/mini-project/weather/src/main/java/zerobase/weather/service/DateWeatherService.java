package zerobase.weather.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.client.WeatherClient;
import zerobase.weather.client.dto.Weather;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.repository.DateWeatherRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DateWeatherService {

    private final DateWeatherRepository dateWeatherRepository;
    private final WeatherClient weatherClient;

    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherDate() {
        dateWeatherRepository.save(getWeatherFromApi(LocalDate.now()));
    }

    public DateWeather getDateWeather(LocalDate date) {
        List<DateWeather> dateWeathersFromDB = dateWeatherRepository.findAllByDate(date);
        if (dateWeathersFromDB.isEmpty()) {
            return getWeatherFromApi(date);
        }

        return dateWeathersFromDB.get(0);
    }

    private DateWeather getWeatherFromApi(LocalDate date) {
        Weather weather = weatherClient.getWeatherData();
        return DateWeather.builder()
            .date(date)
            .weather(weather.getWeather())
            .icon(weather.getIcon())
            .temperature(weather.getTemp())
            .build();
    }
}
