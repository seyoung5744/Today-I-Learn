package kuke.board.articleread.cache;

import lombok.Getter;

import java.time.Duration;

// TTL 을 받아 Logical TTL 과 Physical TTL 계산
@Getter
public class OptimizedCacheTTL {

    private Duration logicalTTL;
    private Duration physicalTTL;

    // physicalTTL 은 logicalTTL 보다 만료 시간이 김
    public static final long PHYSICAL_TTL_DELAY_SECONDS = 5;

    public static OptimizedCacheTTL of(long ttlSeconds) {
        OptimizedCacheTTL optimizedCacheTTL = new OptimizedCacheTTL();
        optimizedCacheTTL.logicalTTL = Duration.ofSeconds(ttlSeconds);
        optimizedCacheTTL.physicalTTL = optimizedCacheTTL.logicalTTL.plusSeconds(PHYSICAL_TTL_DELAY_SECONDS);
        return optimizedCacheTTL;
    }
}
