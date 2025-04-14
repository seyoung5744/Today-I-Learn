package hello.springtx.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AsyncTransactionTest2 {

    @Autowired
    LookupService lookupService;

    @Test
    void lookupTest() throws InterruptedException {
        log.info("LookupService class={}", lookupService);
        for (int i = 0; i < 100; i++) {
            lookupService.findUser("PivotalSoftware" + i);
        }

        Thread.sleep(10000);
    }
}
