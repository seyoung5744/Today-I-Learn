package zerobase.weather.controller;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.controller.request.CreateDiaryRequest;
import zerobase.weather.service.DiaryService;
import zerobase.weather.service.response.DiaryResponse;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/api/v1/diary")
    public DiaryResponse createDiary(
        @RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date, @RequestBody CreateDiaryRequest request) {
        return diaryService.createDiary(date, request);
    }
}
