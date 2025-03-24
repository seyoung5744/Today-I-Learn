package kr.co.samplecompany.myrestfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyRestfulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRestfulServiceApplication.class, args);

//        ApplicationContext ac = SpringApplication.run(MyRestfulServiceApplication.class, args);
//        for (String beanDefinitionName : ac.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }
    }

}
