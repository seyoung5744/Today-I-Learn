package com.example.springbootmetrics.study;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Counter meter 테스트 용도")
public class CountTests {

    private MeterRegistry meterRegistry;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
    }

    @Test
    @DisplayName("Counter 의 Increment 동작 확인용")
    void testCounterMeter() {
        // given
        Counter counter = Counter.builder("counter.test")
                .register(meterRegistry);

        // when
        counter.increment(3.0);

        // then
        assertAll(
                () -> assertThat(counter.count()).isEqualTo(3.0),
                () -> assertThat(counter.getId().getName()).isEqualTo("counter.test")
        );
    }
}
