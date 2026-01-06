package kuke.board.articleread.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kuke.board.common.dataserializer.DataSerializer;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
public class OptimizedCache {

    private String data; // 문자열로 된 데이터
    private LocalDateTime expiredAt; // 만료 시간을 데이터가 직접 갖고 있는다. 즉, Logical TTL 에 의해서 만료되는 시간

    /**
     * @param ttl Logical TTL
     */
    public static OptimizedCache of(Object data, Duration ttl) {
        OptimizedCache optimizedCache = new OptimizedCache();
        optimizedCache.data = DataSerializer.serialize(data);
        optimizedCache.expiredAt = LocalDateTime.now().plus(ttl);
        return optimizedCache;
    }

    // Logical TTL 이 만료되었는 지 확인

    /**
     * Jackson 직렬화 결과 (❌ 의도치 않은 JSON)
     * {
     * "data": "hello",
     * "expiredAt": "2026-01-06T14:30:00",
     * "expired": false
     * }
     * 👉 expired 필드가 자동으로 추가됨
     */
    @JsonIgnore // 해당 메서드는 데이터 직렬화 시 필드에 포함될 수도 있기 때문에 @JsonIgnore 으로 문자열로 변환되는 것을 방지
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    public <T> T parseData(Class<T> dataType) {
        return DataSerializer.deserialize(data, dataType);
    }
}
