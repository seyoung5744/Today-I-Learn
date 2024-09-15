package zerobase.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.controller.request.CreateDiaryRequest;
import zerobase.weather.controller.request.EditDiaryRequest;
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

    @GetMapping("/api/v1/diary")
    public List<DiaryResponse> readDiary(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return diaryService.readDiary(date);
    }

    @Operation(summary = "날짜 읽기")
    @GetMapping("/api/v1/diaries")
    public List<DiaryResponse> readDiaries(
        @Parameter(description = "날짜 형식 : yyyy-MM-dd") @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate
    ) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @PatchMapping("/api/v1/diary")
    public DiaryResponse updateDiary(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date, @RequestBody EditDiaryRequest request) {
        return diaryService.updateDiary(date, request);
    }

    @DeleteMapping("/api/v1/diary")
    public void deleteDiary(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        diaryService.deleteDiary(date);
    }
}
