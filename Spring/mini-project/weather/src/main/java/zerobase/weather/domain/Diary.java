package zerobase.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weather;
    private String icon;
    private double temperature;
    private String text;
    private LocalDate date;

    @Builder
    private Diary(String weather, String icon, double temperature, String text, LocalDate date) {
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
        this.text = text;
        this.date = date;
    }

    public static Diary create(String weather, String icon, double temperature, String text, LocalDate date) {
        return Diary.builder()
            .weather(weather)
            .icon(icon)
            .temperature(temperature)
            .text(text)
            .date(date)
            .build();
    }

    public void editText(String text) {
        this.text = text;
    }
}
