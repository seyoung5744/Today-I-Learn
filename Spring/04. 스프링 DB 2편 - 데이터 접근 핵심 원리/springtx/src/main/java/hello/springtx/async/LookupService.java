package hello.springtx.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LookupService {

    @Async
    public void findUser(String user) throws InterruptedException {
        log.info("[조회 서비스 {}] {}", user, Thread.currentThread().getName());
//        Thread.sleep(1000L);
    }
}
