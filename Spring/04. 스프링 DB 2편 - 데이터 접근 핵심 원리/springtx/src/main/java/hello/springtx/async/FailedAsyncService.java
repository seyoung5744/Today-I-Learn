//package hello.springtx.async;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//@Slf4j
//@Service
//public class FailedAsyncService {
//
//    @Transactional
//    @Async
//    public void doAsyncTransactional() {
//        boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
//        log.info("[FAIL] 트랜잭션 활성 상태? {}", txActive); // ❌ false일 확률 높음
//        log.info("current tx name: {}", TransactionSynchronizationManager.getCurrentTransactionName());
//    }
//}
