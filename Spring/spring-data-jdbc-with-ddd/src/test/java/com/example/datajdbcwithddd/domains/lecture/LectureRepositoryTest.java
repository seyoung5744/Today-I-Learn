package com.example.datajdbcwithddd.domains.lecture;

import com.example.datajdbcwithddd.domains.term.Term;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LectureRepositoryTest {

    @Autowired
    LectureRepository lectureRepository;

    @Test
    void saveAndFind() {

        Lecture aLecture = Lecture.create("Spring Data Jdbc", Term.of("2021 Q2"));

        Lecture saved = lectureRepository.save(aLecture);

        assertThat(saved).isSameAs(aLecture);
        assertThat(saved.getId()).isNotNull();

        Optional<Lecture> find = lectureRepository.findById(saved.getId());

        assertThat(find.orElse(null)).isNotSameAs(aLecture);
    }
}