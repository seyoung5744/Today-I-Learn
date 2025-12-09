package com.example.springbootbatchsample.infrastructure;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TestDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.test")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public PlatformTransactionManager testTransactionManager(@Qualifier("testEntityManagerFactory") EntityManagerFactory testEntityManagerFactory) {
        JpaTransactionManager testTransactionManager = new JpaTransactionManager();
        testTransactionManager.setEntityManagerFactory(testEntityManagerFactory);
        return testTransactionManager;
    }
}
