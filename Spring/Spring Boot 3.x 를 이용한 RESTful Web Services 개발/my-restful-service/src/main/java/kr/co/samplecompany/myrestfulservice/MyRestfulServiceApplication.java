package kr.co.samplecompany.myrestfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class MyRestfulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRestfulServiceApplication.class, args);

//        ApplicationContext ac = SpringApplication.run(MyRestfulServiceApplication.class, args);
//        for (String beanDefinitionName : ac.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
