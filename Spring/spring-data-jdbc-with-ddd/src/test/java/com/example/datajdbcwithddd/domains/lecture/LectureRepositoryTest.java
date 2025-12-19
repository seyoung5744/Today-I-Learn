package com.example.datajdbcwithddd.domains.lecture;

import com.example.datajdbcwithddd.domains.term.Qtr;
import com.example.datajdbcwithddd.domains.term.TermId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LectureRepositoryTest {

    @Autowired
    LectureRepository lectureRepository;

    @Test
    void saveAndFind() {

        TermId termId = TermId.of(Year.of(2025), Qtr.Q1);
        LectureId lectureId = termId.createLectureId(1);
        CreateLecture command = new CreateLecture(lectureId, termId, "Spring Data Jdbc");

        Lecture aLecture = Lecture.create(command);

        Lecture saved = lectureRepository.save(aLecture);

        assertThat(saved).isSameAs(aLecture);
        assertThat(saved.getId()).isNotNull();

        Lecture find = lectureRepository.findById(saved.getId());

        assertThat(find).isNotSameAs(aLecture);
    }
}