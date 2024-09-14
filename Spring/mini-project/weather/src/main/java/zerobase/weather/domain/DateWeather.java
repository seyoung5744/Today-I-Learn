package zerobase.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateWeather {

    @Id
    private LocalDate date;
    private String weather;
    private String icon;
    private double temperature;

    @Builder
    public DateWeather(LocalDate date, String weather, String icon, double temperature) {
        this.date = date;
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
    }
}
