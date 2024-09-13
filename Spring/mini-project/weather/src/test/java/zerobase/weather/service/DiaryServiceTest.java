package zerobase.weather.service;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = "openweathermap.key : 1234")
class DiaryServiceTest {

    @Autowired
    private DiaryService diaryService;

    @DisplayName("startDate 가 endDate 이후 날짜면 에러를 반환한다.")
    @Test
    void readDiariesStartDateIsAfterEndDate() {
        // given
        LocalDate startDate = LocalDate.of(2024, 8, 10);
        LocalDate endDate = LocalDate.of(2024, 8, 9);

        // when // then
        Assertions.assertThatThrownBy(() -> diaryService.readDiaries(startDate, endDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("날짜 입력이 잘못되었습니다.");
    }
}