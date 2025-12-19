package com.example.datajdbcwithddd.domains.term;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Qtr {

    Q1(1),
    Q2(2),
    Q3(3),
    Q4(4);

    final int value;

    public static Qtr of(int n) {
        return Arrays.stream(values()).filter(q -> q.value == n).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
