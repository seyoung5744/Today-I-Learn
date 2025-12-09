package com.example.springbootbatchsample.infrastructure;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "com.example.springbootbatchsample.domain.repository"
        },
        entityManagerFactoryRef = "testEntityManagerFactory",
        transactionManagerRef = "testTransactionManager"
)
public class TestDataManagerConfig {

    private final DataSource testDataSource;

    public TestDataManagerConfig(@Qualifier("testDataSource") DataSource testDataSource) {
        this.testDataSource = testDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean testEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        em.setDataSource(this.testDataSource);
        em.setPersistenceUnitName("testEntityManager");
        em.setPackagesToScan("com.example.springbootbatchsample.domain.entity");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(testJpaProperties());
        em.afterPropertiesSet();
        return em;
    }

    @Bean
    public JdbcTemplate testJdbcTemplate(@Qualifier("testDataSource") DataSource testDataSource) {
        return new JdbcTemplate(testDataSource);
    }

    private Properties testJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");
        properties.setProperty(AvailableSettings.ALLOW_UPDATE_OUTSIDE_TRANSACTION, "treu");
        return properties;
    }
}
