package kuke.board.articleread.api;

import kuke.board.articleread.service.response.ArticleReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleReadApiTest {

    RestClient restClient = RestClient.create("http://localhost:8005");

    @Test
    void readTest() {
        // given
        ArticleReadResponse response = restClient.get()
                .uri("/v1/articles/{articleId}", 266154838979391488L)
                .retrieve()
                .body(ArticleReadResponse.class);
        System.out.println("response = " + response);
    }
}
