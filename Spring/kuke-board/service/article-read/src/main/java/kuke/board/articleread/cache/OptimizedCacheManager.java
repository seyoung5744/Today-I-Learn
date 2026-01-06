package kuke.board.articleread.cache;

import kuke.board.common.dataserializer.DataSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

@Component
@RequiredArgsConstructor
public class OptimizedCacheManager {

    private final StringRedisTemplate redisTemplate;
    private final OptimizedCacheLockProvider optimizedCacheLockProvider;

    private static final String DELIMITER = "::";

    /**
     * @param type               캐시 타입
     * @param ttlSeconds         ttl 시간
     * @param args               캐시에 대해 유니크하게 구분하기 위한 (타입별로 파라미터가 다르므로) args
     * @param returnType         return 객체인 Object 에 대한 type
     * @param originDataSupplier 캐시가 만료되었을 떄 원본 데이터들를 가져오기 위해 요청할 수 있는 메서드들을 갖고 있음
     * @return 캐시 or 원본 데이터에 대해 처리된 결과
     */
    public Object process(String type, long ttlSeconds, Object[] args, Class<?> returnType,
                          OptimizedCacheOriginDataSupplier<?> originDataSupplier) throws Throwable {
        String key = generateKey(type, args);
        String cachedData = redisTemplate.opsForValue().get(key);

        // 원본 데이터를 요청 후 캐시 적재
        if (cachedData == null) {
            return refresh(originDataSupplier, key, ttlSeconds);
        }

        OptimizedCache optimizedCache = DataSerializer.deserialize(cachedData, OptimizedCache.class);
        // 역직렬화 실패 시 원본 데이터 재요청
        if (optimizedCache == null) {
            return refresh(originDataSupplier, key, ttlSeconds);
        }

        // optimizedCache 가 논리적으로 만료되지 않았다면 optimizedCache 의 데이터를 캐스팅하고 반환.
        if (!optimizedCache.isExpired()) {
            return optimizedCache.parseData(returnType);
        }

        // 캐시가 논리적으로 만료되엇으므로 캐시 갱신. 이때 1건의 요청만 가능하도록 분산락 이용
        if (!optimizedCacheLockProvider.lock(key)) {
            return optimizedCache.parseData(returnType); // lock 을 잡지 못했다면 일단 (과거)data는 갖고 있으니 그대로 반환
        }

        try {
            return refresh(originDataSupplier, key, ttlSeconds);
        } finally {
            optimizedCacheLockProvider.unlock(key);
        }

    }

    private Object refresh(OptimizedCacheOriginDataSupplier<?> originDataSupplier, String key, long ttlSeconds) throws Throwable {
        // OptimizedCacheOriginDataSupplier 는 원본 데이터에 접근하는 방법은 알고 있으므로 바로 get 요청
        Object result = originDataSupplier.get();

        OptimizedCacheTTL optimizedCacheTTL = OptimizedCacheTTL.of(ttlSeconds);
        OptimizedCache optimizedCache = OptimizedCache.of(result, optimizedCacheTTL.getLogicalTTL());

        redisTemplate.opsForValue()
                .set(
                        key,
                        DataSerializer.serialize(optimizedCache),
                        optimizedCacheTTL.getPhysicalTTL() // 실제 물리적으로 만료되는 ttl
                );
        return result;
    }

    private String generateKey(String prefix, Object[] args) {
        // prefix = a, args = [1, 2]
        // key = a::1::2
        return prefix + DELIMITER +
                Arrays.stream(args)
                        .map(String::valueOf)
                        .collect(joining(DELIMITER));
    }

}
