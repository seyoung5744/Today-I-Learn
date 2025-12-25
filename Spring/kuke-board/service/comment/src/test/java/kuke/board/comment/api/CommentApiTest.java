package kuke.board.comment.api;

import kuke.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:8001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("commentId = " + response1.getCommentId());
        System.out.println("\tcommentId = " + response2.getCommentId());
        System.out.println("\tcommentId = " + response3.getCommentId());
    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commetId}", 262406095561314304L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
//        commentId = 262406095561314304
//        commentId = 262406096903491584
//        commentId = 262406096995766272
        restClient.delete()
                .uri("/v1/comments/{commentId}", 262406096995766272L)
                .retrieve();
    }

    @Getter
    @AllArgsConstructor
    static class CommentCreateRequest {

        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
