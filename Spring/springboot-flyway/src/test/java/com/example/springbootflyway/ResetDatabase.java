package com.example.springbootflyway;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResetDatabase {

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        // db clean - flyway clean
        flyway.clean();
        // db migrate - flyway migrate
        flyway.migrate();
    }
}
