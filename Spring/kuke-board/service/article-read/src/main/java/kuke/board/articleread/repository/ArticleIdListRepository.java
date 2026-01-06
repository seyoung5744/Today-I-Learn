package kuke.board.articleread.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Redis 에 게시글 ID만 따로 sorted set 에 저장하기 위한 repository
 */
@Repository
@RequiredArgsConstructor
public class ArticleIdListRepository {

    private final StringRedisTemplate redisTemplate;

    // article-read::board::{boardId}::article-list
    private static final String KEY_FORMAT = "article-read::board::%s::article-list";

    public void add(Long boardId, Long articleId, Long limit) {
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            String key = generateKey(boardId);
            /*
             * [articleId 정렬 로직 결정 사유]
             *
             * 1. 문제
             *    Redis ZSet의 score는 double 타입이므로, long 타입인 articleId를 score로 직접 사용하면
             *    정밀도 손실(Precision Loss)로 인해 정렬 순서가 왜곡될 수 있다.
             *
             * 2. 해결
             *    score는 0으로 고정하고, articleId는 value 영역에 저장한다.
             *
             * 3. 정렬 보장 방식
             *    ZSet은 score가 동일한 경우 value를 사전식(Lexicographical) 순서로 정렬한다.
             *
             * 4. Padding 적용 이유
             *    articleId를 단순 문자열로 저장하면 "10"이 "2"보다 앞서는 문제가 발생할 수 있으므로,
             *    toPaddedString을 사용해 숫자 크기와 사전식 정렬 순서를 일치시킨다.
             */
            conn.zAdd(key, 0, toPaddedString(articleId));
            conn.zRemRange(key, 0, -limit - 1); // 상위 limit 개수만 유지
            return null;
        });
    }

    public void delete(Long boardId, Long articleId) {
        redisTemplate.opsForZSet().remove(generateKey(boardId), toPaddedString(articleId));
    }

    // 페이징 방식
    public List<Long> readAll(Long boardId, Long offset, Long limit) {
        return redisTemplate.opsForZSet()
                .reverseRange(generateKey(boardId), offset, offset + limit - 1)
                .stream().map(Long::valueOf)
                .toList();
    }

    // 무한 스크롤 방식
    public List<Long> readAllInfiniteScroll(Long boardId, Long lastArticleId, Long limit) {
        return redisTemplate.opsForZSet().reverseRangeByLex(
                generateKey(boardId),
                lastArticleId == null ?
                        Range.unbounded() :
                        Range.leftUnbounded(Range.Bound.exclusive(toPaddedString(lastArticleId))),
                Limit.limit().count(limit.intValue())
        ).stream().map(Long::valueOf).toList();
    }

    // Long 파라미터 -> 고정 자릿수 문자열 (빈 자리는 0으로 채움)
    private String toPaddedString(Long articleId) {
        // 1234 -> 0000000000000001234
        return "%019d".formatted(articleId);
    }

    private String generateKey(Long boardId) {
        return KEY_FORMAT.formatted(boardId);
    }
}
