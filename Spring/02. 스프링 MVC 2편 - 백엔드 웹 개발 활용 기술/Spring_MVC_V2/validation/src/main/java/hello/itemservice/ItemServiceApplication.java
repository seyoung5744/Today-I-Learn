package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//public class ItemServiceApplication implements WebMvcConfigurer {
public class ItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }

//    @Override
//    public Validator getValidator() {
//        return new ItemValidator();
//    }
}
