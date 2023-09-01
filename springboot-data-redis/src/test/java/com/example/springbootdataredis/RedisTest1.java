package com.example.springbootdataredis;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootdataredis.config.TestEmbeddedRedisConfig;
import com.example.springbootdataredis.domain.Point;
import com.example.springbootdataredis.domain.PointRedisRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestEmbeddedRedisConfig.class)
public class RedisTest1 {

    @Autowired
    PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회하기() {
        //given
        String id = "testID";
        LocalDateTime refreshTime = LocalDateTime.of(2023, 9, 1, 0, 0);
        Point point = Point.builder()
            .id(id)
            .amount(1000L)
            .refreshTime(refreshTime)
            .build();

        //when
        pointRedisRepository.save(point);

        //then
        Point savedPoint = pointRedisRepository.findById(id).get();

        assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }

    @Test
    public void 수정기능() {
        //given
        String id = "testID";
        LocalDateTime refreshTime = LocalDateTime.of(2023, 9, 1, 0, 0);
        pointRedisRepository.save(Point.builder()
            .id(id)
            .amount(1000L)
            .refreshTime(refreshTime)
            .build());

        //when
        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2023, 9, 1, 21, 30));
        pointRedisRepository.save(savedPoint);

        //then
        Point refreshPoint = pointRedisRepository.findById(id).get();
        assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
    }
}
