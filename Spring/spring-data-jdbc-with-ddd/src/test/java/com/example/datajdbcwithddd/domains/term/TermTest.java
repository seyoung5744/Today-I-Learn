package com.example.datajdbcwithddd.domains.term;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TermTest {

    @Test
    void 학기의_최대_개설_강의_수를_초과하였습니다() {

        Term aTerm = Term.of("2025-01", 1);
        aTerm.add(AddLecture.of("Spring Data Jdbc"));

        assertThatThrownBy(() -> aTerm.add(AddLecture.of("Spring Data Jdbc")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("학기의 최대 개설 강의 수를 초과하였습니다.");
    }
}