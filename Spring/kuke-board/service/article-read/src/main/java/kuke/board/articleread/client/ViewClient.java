package kuke.board.articleread.client;

import jakarta.annotation.PostConstruct;
import kuke.board.articleread.cache.OptimizedCacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewClient {

    private RestClient restClient;

    @Value("${endpoints.board-view-service.url}")
    private String viewServiceUrl;

    @PostConstruct
    public void initRestClient() {
        restClient = RestClient.create(viewServiceUrl);
    }

    // 레디스에서 데이터 조회
    // 레디스에 데이터가 없다면, count 메서드가 호출되면서 viewService로 원본 데이터 요청. 그리고 레디스에 데이터를 저장하고 응답
    // 레디스에 데이터가 있었다면, 해당 데이터를 그대로 바로 응답
//    @Cacheable(key = "#articleId", value = "articleViewCount")
    @OptimizedCacheable(type = "articleViewCount", ttlSeconds = 1)
    public Long count(Long articleId) {
        log.info("[ViewClient.count] articleId={}", articleId);
        try {
            return restClient.get()
                    .uri("/v1/article-views/articles/{articleId}/count", articleId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[ViewClient.count] articleId={}", articleId, e);
            return 0L;
        }
    }

}
