package zerobase.weather.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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


    public List<DiaryResponse> readDiary(LocalDate date) {
        List<Diary> diaries = diaryRepository.findAllByDate(date);
        return diaries.stream()
            .map(DiaryResponse::of)
            .collect(Collectors.toList());
    }

    public List<DiaryResponse> readDiaries(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("날짜 입력이 잘못되었습니다.");
        }

        List<Diary> diaries = diaryRepository.findAllByDateBetween(startDate, endDate);
        return diaries.stream()
            .map(DiaryResponse::of)
            .collect(Collectors.toList());
    }
}
