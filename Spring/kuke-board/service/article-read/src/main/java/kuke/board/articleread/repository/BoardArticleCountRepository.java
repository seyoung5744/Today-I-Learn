package kuke.board.articleread.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 페이징 목록 조회 방식의 게시글 개수 반환 Repository
 * 원래, 게시글 개수를 알기 위해 Article Service 에 요청을 해야하지만,
 * 이조차 방지하기 위해 Article-Read Service 가 직접 관리
 * (게시글 수도 이벤트를 통해 전달 받고 있는 상황)
 */
@Repository
@RequiredArgsConstructor
public class BoardArticleCountRepository {

    private final StringRedisTemplate redisTemplate;

    // article-read::board-article-count::board::{boardId}
    private static final String KEY_FORMAT = "article-read::board-article-count::board::%s";

    public void createOrUpdate(Long boardId, Long articleCount) {
        redisTemplate.opsForValue().set(generateKey(boardId), String.valueOf(articleCount));
    }

    public Long read(Long boardId) {
        String result = redisTemplate.opsForValue().get(generateKey(boardId));
        return result == null ? 0L : Long.parseLong(result);
    }

    private String generateKey(Long boardId) {
        return KEY_FORMAT.formatted(boardId);
    }
}
