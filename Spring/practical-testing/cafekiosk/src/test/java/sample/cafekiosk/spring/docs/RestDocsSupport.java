package sample.cafekiosk.spring.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(RestDocumentationExtension.class)
//@SpringBootTest
public abstract class RestDocsSupport {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    /**
     * WebApplicationContext 는 스프링의 컨텍스트를 얘기한다. 그런데 webApplicationContext를 파라미터로 받게 되면
     *
     * @SpringBootTest 로 RestDocs 를 작성하는 법을 뜻한다.
     * <p>
     * 하지만 이렇게 되면 문서를 작성할 때도 스프링 서버를 띄우는 식이 되어버려서 굳이 그럴 필요가 없기 때문에 standaloneSetup() 를 사용하겠다.
     */
//    @BeforeEach
//    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//            .apply(documentationConfiguration(provider))
//            .build();
//    }
    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
            .apply(documentationConfiguration(provider))
            .build();
    }

    protected abstract Object initController();

}
