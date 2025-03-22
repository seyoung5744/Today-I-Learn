package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Test
    void lifeCycleTest2() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig2.class);
        NetworkClient2 client = ac.getBean(NetworkClient2.class);
        ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Test
    void lifeCycleTest3() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig3.class);
        NetworkClient3 client = ac.getBean(NetworkClient3.class);
        ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Test
    void lifeCycleTest4() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig4.class);
        NetworkClient4 client = ac.getBean(NetworkClient4.class);
        ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

    @Configuration
    static class LifeCycleConfig2 {

        @Bean
        public NetworkClient2 networkClient2() {
            NetworkClient2 networkClient2 = new NetworkClient2();
            networkClient2.setUrl("http://hello-spring.dev");
            return networkClient2;
        }

    }

    @Configuration
    static class LifeCycleConfig3 {

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient3 networkClient3() {
            NetworkClient3 networkClient3 = new NetworkClient3();
            networkClient3.setUrl("http://hello-spring.dev");
            return networkClient3;
        }

    }

    @Configuration
    static class LifeCycleConfig4 {

        @Bean
        public NetworkClient4 networkClient4() {
            NetworkClient4 networkClient4 = new NetworkClient4();
            networkClient4.setUrl("http://hello-spring.dev");
            return networkClient4;
        }

    }
}
