package kuke.board.articleread.service.event.handler;

import kuke.board.articleread.repository.ArticleIdListRepository;
import kuke.board.articleread.repository.ArticleQueryModel;
import kuke.board.articleread.repository.ArticleQueryModelRepository;
import kuke.board.articleread.repository.BoardArticleCountRepository;
import kuke.board.common.event.Event;
import kuke.board.common.event.EventType;
import kuke.board.common.event.payload.ArticleCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {

    private final ArticleQueryModelRepository articleQueryModelRepository;
    private final ArticleIdListRepository articleIdListRepository;
    private final BoardArticleCountRepository boardArticleCountRepository;

    @Override
    public void handle(Event<ArticleCreatedEventPayload> event) {
        ArticleCreatedEventPayload payload = event.getPayload();
        /*
            처리 순서 중요!!

            articleQueryModelRepository를 먼저 호출해야 게시글 목록에 노출되었을 때 실제 게시글 내용이
            정상적으로 조회 가능한 상태가 된다.

            만약 articleIdListRepository를 먼저 호출하면, 게시글 ID는 목록에 존재하지만

            아직 게시글 본문이 생성되지 않아 불완전한 조회 상태가 발생할 수 있다.

            따라서 게시글 내용(조회 모델)을 먼저 생성한 뒤, 게시글 ID를 목록에 추가하여
            목록과 내용의 일관성을 보장한다.
         */
        articleQueryModelRepository.create(
                ArticleQueryModel.create(payload),
                Duration.ofDays(1)
        );
        articleIdListRepository.add(payload.getBoardId(), payload.getArticleId(), 1000L);
        boardArticleCountRepository.createOrUpdate(payload.getBoardId(), payload.getBoardArticleCount());
    }

    @Override
    public boolean support(Event<ArticleCreatedEventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }
}
