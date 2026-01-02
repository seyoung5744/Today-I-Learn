package kuke.board.common.outboxmessagerelay;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    // 아직 전송되지 않은 이벤트들을 주기적으로 polling해서 전송하기 위한 조회 메서드
    List<Outbox> findAllByShardKeyAndCreatedAtLessThanEqualOrderByCreatedAtDesc(
            Long shardKey,
            LocalDateTime from,
            Pageable pageable
    );
}
