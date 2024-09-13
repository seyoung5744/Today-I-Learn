package zerobase.weather.service.response;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.weather.domain.Diary;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryResponse {

    private Long id;
    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;

    @Builder
    private DiaryResponse(Long id, String weather, String icon, double temperature, String text, LocalDate date) {
        this.id = id;
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
        this.text = text;
        this.date = date;
    }

    public static DiaryResponse of(Diary diary) {
        return DiaryResponse.builder()
            .id(diary.getId())
            .weather(diary.getWeather())
            .icon(diary.getIcon())
            .temperature(diary.getTemperature())
            .text(diary.getText())
            .date(diary.getDate())
            .build();
    }
}
