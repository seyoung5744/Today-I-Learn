package zerobase.weather.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.weather.client.WeatherClient;
import zerobase.weather.client.dto.Weather;
import zerobase.weather.controller.request.CreateDiaryRequest;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;
import zerobase.weather.service.response.DiaryResponse;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final WeatherClient weatherClient;
    private final DiaryRepository diaryRepository;

    public DiaryResponse createDiary(LocalDate date, CreateDiaryRequest request) {
        Weather weather = weatherClient.getWeatherData();
        Diary diary = Diary.create(weather.getWeather(), weather.getIcon(), weather.getTemp(), request.getText(), date);
        Diary savedDiary = diaryRepository.save(diary);
        return DiaryResponse.of(savedDiary);
    }


}
