package com.zerobase.consumer.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.zerobase.domain.repository"})
@EntityScan(basePackages = {"com.zerobase.domain"})
public class JpaAuditingConfig {

}
