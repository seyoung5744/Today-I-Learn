package zerobase.weather.client.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Weather {

    private String weather;
    private String icon;
    private double temp;

    public Weather(String weather, String icon, double temp) {
        this.weather = weather;
        this.icon = icon;
        this.temp = temp;
    }
}
