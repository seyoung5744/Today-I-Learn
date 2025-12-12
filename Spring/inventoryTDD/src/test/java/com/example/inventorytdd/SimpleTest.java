package com.example.inventorytdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SimpleTest {

    public int fibonacci(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("negative value is not allowed");
        }
        if (num <= 1) {
            return num;
        }

        return fibonacci(num - 1) + fibonacci(num - 2);
    }

    @Test
    void testFibonacci_0() {
        int num = 0;
        int result = fibonacci(num);

        // 0 -> 0
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testFibonacci_1() {
        int num = 1;
        int result = fibonacci(num);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void testFibonacci_2() {
        int num = 2;
        int result = fibonacci(num);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void testFibonacci_3() {
        int num = 3;
        int result = fibonacci(num);

        assertThat(result).isEqualTo(2);
    }

    @Test
    void testFibonacci_minus1() {
        int num = -1;
        assertThatThrownBy(() -> fibonacci(num))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testFibonacci_10() {
        int num = 10;
        int result = fibonacci(num);

        assertThat(result).isEqualTo(55);
    }
}
