package hello.springtx.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AsyncTransactionTest {

    @Autowired
    FailedAsyncService failedAsyncService;

    @Autowired
    SuccessAsyncCaller successAsyncCaller;

    @Test
    void failedAsyncTransactionTest() throws InterruptedException {
        log.info("failedAsyncTransactionTest");
        failedAsyncService.doAsyncTransactional();

        Thread.sleep(3000); // 비동기 메서드 대기
    }

    @Test
    void successAsyncTransactionTest() throws InterruptedException {
        successAsyncCaller.callAsync();

        Thread.sleep(1000); // 비동기 메서드 대기
    }
}
