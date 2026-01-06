package kuke.board.articleread.service.event.handler;

import kuke.board.articleread.repository.ArticleIdListRepository;
import kuke.board.articleread.repository.ArticleQueryModelRepository;
import kuke.board.articleread.repository.BoardArticleCountRepository;
import kuke.board.common.event.Event;
import kuke.board.common.event.EventType;
import kuke.board.common.event.payload.ArticleDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {

    private final ArticleQueryModelRepository articleQueryModelRepository;
    private final ArticleIdListRepository articleIdListRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        /*
            articleIdListRepository → articleQueryModelRepository 호출 순서 중요

            articleQueryModelRepository를 먼저 호출하면 사용자는 게시글 목록 조회가 가능한 상태가 된다.
            이 상태에서 articleIdListRepository를 이후에 호출하면, 목록은 존재하지만 실제 게시글 내용이 삭제되어
            불완전한 조회 상태가 발생할 수 있다.

            따라서 게시글 목록 접근을 먼저 차단하기 위해 articleIdListRepository를 선행 호출하고,
            이후 articleQueryModelRepository를 호출하여 게시글 내용까지 안전하게 삭제한다.
            (물론 Redis 연산이고 찰나의 순간이라서 대부분의 경우 이러한 문제는 발생하지 않을 확률이 높다.)
         */
        articleIdListRepository.delete(payload.getBoardId(), payload.getArticleId());
        articleQueryModelRepository.delete(payload.getArticleId());

        boardArticleCountRepository.createOrUpdate(payload.getArticleId(), payload.getBoardArticleCount());
    }

    @Override
    public boolean support(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }
}
