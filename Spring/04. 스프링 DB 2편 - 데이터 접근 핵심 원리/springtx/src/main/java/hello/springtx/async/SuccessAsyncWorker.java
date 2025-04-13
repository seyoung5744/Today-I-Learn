package hello.springtx.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
public class SuccessAsyncWorker {

    @Async
    @Transactional
    public void asyncTransactionalMethod() {
        boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
        log.info("[SUCCESS] 트랜잭션 활성 상태? {}", txActive); // ✅ true!
    }
}
