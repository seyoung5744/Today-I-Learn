package com.example.datajdbcwithddd.domains.term;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RecordApplicationEvents
public class TermEventPublishTest {

    @Autowired
    TermRepository termRepository;

    @Autowired
    ApplicationEvents applicationEvents;

    @Test
    void publish() {
        Term aTerm = Term.of(Year.of(2021), Qtr.Q1, 1);
        aTerm.add(AddLecture.of("Spring Data Jdbc"));

        termRepository.save(aTerm);

        assertThat(applicationEvents.stream(LectureAdded.class)).hasSize(1);
        System.out.println("Test " + Thread.currentThread());
    }
}
