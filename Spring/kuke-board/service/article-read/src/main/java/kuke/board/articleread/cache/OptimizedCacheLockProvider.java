package kuke.board.articleread.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

// 캐시 갱신 요청은 분산락을 통해 1건의 요청만 처리되도록 한다.
@Component
@RequiredArgsConstructor
public class OptimizedCacheLockProvider {

    private final StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "optimized-cache-lock::";
    private static final Duration LOCK_TTL = Duration.ofSeconds(3); // 원본 데이터 갱신은 3초면 충분하다는 판단하에 3초로 설정

    public boolean lock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(
                generateLockKey(key),
                "",
                LOCK_TTL
        );
    }

    public void unlock(String key) {
        redisTemplate.delete(generateLockKey(key));
    }

    private String generateLockKey(String key) {
        return KEY_PREFIX + key;
    }

}
