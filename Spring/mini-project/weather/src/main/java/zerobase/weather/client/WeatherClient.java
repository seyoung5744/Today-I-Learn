package zerobase.weather.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import zerobase.weather.client.dto.Weather;

@Slf4j
@Component
public class WeatherClient {

    @Value("${openweathermap.key}")
    private String apiKey;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public WeatherClient(RestClient.Builder builder, ObjectMapper objectMapper) {
        this.restClient = builder
            .baseUrl("https://api.openweathermap.org/data/2.5/weather?q=seoul")
            .build();
        this.objectMapper = objectMapper;
    }

    public Weather getWeatherData() {
        String weatherData = getWeatherJson();
        return parseWeather(weatherData);
    }

    private Weather parseWeather(String jsonString) {

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode weatherNode = jsonNode.get("weather").get(0);
            JsonNode mainNode = jsonNode.get("main");

            return new Weather(
                weatherNode.get("main").asText(),
                weatherNode.get("icon").asText(),
                mainNode.get("temp").asDouble()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getWeatherJson() {
        return restClient.get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("appid", apiKey)
                .build())
            .retrieve()
            .body(String.class);
    }


}
