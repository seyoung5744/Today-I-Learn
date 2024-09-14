package zerobase.weather.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.controller.request.CreateDiaryRequest;
import zerobase.weather.controller.request.EditDiaryRequest;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;
import zerobase.weather.service.response.DiaryResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DateWeatherService dateWeatherService;

    public DiaryResponse createDiary(LocalDate date, CreateDiaryRequest request) {
        DateWeather dateWeather = dateWeatherService.getDateWeather(date);
        Diary diary = Diary.create(dateWeather, request.getText());
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

    @Transactional
    public DiaryResponse updateDiary(LocalDate date, EditDiaryRequest request) {
        Diary diary = diaryRepository.findFirstByDate(date)
            .orElseThrow(() -> new IllegalArgumentException(date + " 일기가 존재하지 않습니다."));

        diary.editText(request.getText());

        return DiaryResponse.of(diary);
    }

    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }
}
