package zerobase.weather.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import zerobase.weather.client.dto.Weather;

@ActiveProfiles("test")
@RestClientTest(value = WeatherClient.class, properties = "openweathermap.key=rewrewn34234")
class WeatherClientTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private WeatherClient weatherClient;

    @Test
    void getWeatherDate() throws JsonProcessingException {
        // given
        String url = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=rewrewn34234";
        Map<String, Object> map = new HashMap<>();
        map.put("weather", List.of(Map.of("main", "Rain", "icon", "10n")));
        map.put("main", Map.of("temp", 299.89));
        String json = objectMapper.writeValueAsString(map);

        server.expect(requestTo(url))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        // when
        Weather weatherData = weatherClient.getWeatherData();

        // then
        assertThat(weatherData.getWeather()).isEqualTo("Rain");
        assertThat(weatherData.getIcon()).isEqualTo("10n");
        assertThat(weatherData.getTemp()).isEqualTo(299.89);
    }
}