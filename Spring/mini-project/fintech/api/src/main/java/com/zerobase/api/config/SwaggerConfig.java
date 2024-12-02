//package com.zerobase.api.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//

/*
 * implementation 'io.springfox:springfox-boot-starter:3.0.0' 가 아닌
 * implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2'으로 swagger 동작
 *
 * 참고 : https://velog.io/@kjgi73k/Springboot3%EC%97%90-Swagger3%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
 */
//@Configuration
//public class SwaggerConfig {
//    // http://localhost:8080/swagger-ui/index.html
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//            .useDefaultResponseMessages(false)
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.zerobase.api"))
//            .paths(PathSelectors.any())
//            .build()
//            .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("Swagger")
//            .description("fintech")
//            .version("1.0")
//            .build();
//    }
//}
