//package hello.springtx.async;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
//@EnableAsync
//@Configuration
//public class AsyncConfig {
//
////    @Bean
////    public Executor taskExecutor() {
////        return new SimpleAsyncTaskExecutor();
////    }
//    @Bean("CustomTaskExecutor")
//    public Executor CustomTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("customTaskExecutor-");
//        executor.initialize();
//        return executor;
//    }
//}
