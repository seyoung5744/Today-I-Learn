package com.example.datajdbcwithddd.domains.term;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TermRepositoryTest {

    @Autowired
    TermRepository termRepository;

    @Test
    void saveAndFind() {
        Term aTerm = Term.of("2021-01");
        Term saved = termRepository.save(aTerm);
        Optional<Term> find = termRepository.findById(saved.getId());
        assertThat(find).isPresent();
    }


}