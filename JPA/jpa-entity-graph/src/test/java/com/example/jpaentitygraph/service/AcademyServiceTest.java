package com.example.jpaentitygraph.service;

import com.example.jpaentitygraph.domain.Academy;
import com.example.jpaentitygraph.domain.AcademyRepository;
import com.example.jpaentitygraph.domain.Subject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AcademyServiceTest {

    @Autowired
    AcademyRepository academyRepository;

    @Autowired
    AcademyService academyService;

    @AfterEach
    void tearDown() {
        academyRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        List<Academy> academies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Academy academy = Academy.builder()
                    .name("강남스쿨" + i)
                    .build();

            academy.addSubject(Subject.builder().name("자바웹개발" + i).build());
            academy.addSubject(Subject.builder().name("파이썬자동화" + i).build()); // Subject를 추가
            academies.add(academy);
        }

        academyRepository.saveAll(academies);
    }

    @Test
    public void Academy여러개를_조회시_Subject가_N1_쿼리가발생한다() throws Exception {
        //given
        List<String> subjectNames = academyService.findAllSubjectNames();

        //then
        assertThat(subjectNames.size()).isEqualTo(10);
    }

    @Test
    public void Academy여러개를_joinFetch로_가져온다() throws Exception {
        //given
        List<Academy> academies = academyRepository.findAllJoinFetch();
        List<String> subjectNames = academyService.findAllSubjectNamesByJoinFetch();

        //then
        assertThat(academies.size()).isEqualTo(20); // 20개가 조회!?
        assertThat(subjectNames.size()).isEqualTo(20); // 20개가 조회!?
    }
}