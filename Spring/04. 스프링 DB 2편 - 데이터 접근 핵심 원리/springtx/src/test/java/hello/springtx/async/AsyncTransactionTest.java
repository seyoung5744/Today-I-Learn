package hello.springtx.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@SpringBootTest
public class AsyncTransactionTest {

    @Autowired
    AsyncWorker asyncWorker;

    @Autowired
    NotAsyncWorker notAsyncWorker;

    @Autowired
    AnnotationConfigApplicationContext applicationContext;

    @Autowired
    LookupService lookupService;

    @Test
    void lookupTest() throws InterruptedException {
        log.info("LookupService class={}", lookupService);
        lookupService.findUser("PivotalSoftware");
        lookupService.findUser("CloudFoundry");
        lookupService.findUser("Spring-Projects");
    }

    @Test
    void testAsyncMethod() throws InterruptedException {
        log.info("[메인 스레드] {}", Thread.currentThread().getName());
        log.info("AsyncWorker class={}", asyncWorker.getClass());
        asyncWorker.asyncMethod1();
        asyncWorker.asyncMethod2();

        Thread.sleep(1000);
    }

    @Test
    void testAsyncTransactionalMethod() throws InterruptedException {
        log.info("[메인 스레드] {}", Thread.currentThread().getName());
        log.info("AsyncWorker class={}", asyncWorker.getClass());
        asyncWorker.asyncTransactionalMethod();

        Thread.sleep(1000);
    }

    @Test
    void testAsyncInnerTransactionalMethod() throws InterruptedException {
        log.info("[메인 스레드] {}", Thread.currentThread().getName());
        log.info("AsyncWorker class={}", asyncWorker.getClass());
        asyncWorker.asyncCallInnerTransaction();

        Thread.sleep(1000);
    }


    @Test
    void testNotAsyncMethod() throws InterruptedException {
        log.info("[메인 스레드] {}", Thread.currentThread().getName());
        log.info("NotAsyncWorker class={}", notAsyncWorker.getClass());
        notAsyncWorker.asyncMethod();

        Thread.sleep(1000);
    }

    @Test
    void getAllBeanDefinition() {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            log.info("등록된 빈 {}", bean.getClass());
        }
    }

    @EnableAsync
    @TestConfiguration
    @ComponentScan(basePackageClasses = {AsyncWorker.class, InnerClass.class})
    static class AsyncConfig implements AsyncConfigurer {

        private static final int CORE_POOL_SIZE = 2;
        private static final int MAX_POOL_SIZE = 4;
        private static final int queueCapacity = 20;

        @Override
        public Executor getAsyncExecutor() {
            final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(CORE_POOL_SIZE);
            executor.setMaxPoolSize(MAX_POOL_SIZE);
            executor.setQueueCapacity(queueCapacity);
            executor.setThreadNamePrefix("AsyncConfigurerThreadExecutor-");
            executor.initialize();
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }

        @Bean("CustomTaskExecutor")
        public Executor CustomTaskExecutor() {
            final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(CORE_POOL_SIZE);
            executor.setMaxPoolSize(MAX_POOL_SIZE);
            executor.setQueueCapacity(queueCapacity);
            executor.setThreadNamePrefix("CustomTaskExecutor-");
            executor.initialize();
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }

        @Bean
        NotAsyncWorker notAsyncWorker() {
            return new NotAsyncWorker();
        }
    }

    @Slf4j
    @Component
    static class AsyncWorker {

        private final InnerClass innerClass;

        public AsyncWorker(InnerClass innerClass) {
            this.innerClass = innerClass;
        }

        @Async
        public void asyncMethod1() {
            log.info("[현재 쓰레드] {}", Thread.currentThread().getName());
        }

        @Async("CustomTaskExecutor")
        public void asyncMethod2() {
            log.info("[현재 쓰레드] {}", Thread.currentThread().getName());
        }

        @Async("CustomTaskExecutor")
        @Transactional
        public void asyncTransactionalMethod() {
            log.info("[현재 쓰레드] {}", Thread.currentThread().getName());
            log.info("[트랜잭션 적용 여부] {}", TransactionSynchronizationManager.isActualTransactionActive());
        }

        @Async("CustomTaskExecutor")
        public void asyncCallInnerTransaction() {
            log.info("[현재 쓰레드] {}", Thread.currentThread().getName());
            log.info("InnerClass class={}", innerClass.getClass());
            innerClass.callTransaction();
        }

    }

    @Slf4j
    @Component
    static class InnerClass {

        @Transactional
        public void callTransaction() {
            log.info("InnerClass [트랜잭션 적용 여부] {}", TransactionSynchronizationManager.isActualTransactionActive());
        }
    }

    @Slf4j
    static class NotAsyncWorker {

        //        @Async // 선언되어 있으면 해당 객체의 프록시 생성
        public void asyncMethod() {
            log.info("NotAsyncWorker [현재 쓰레드] {}", Thread.currentThread().getName());
        }
    }

}
