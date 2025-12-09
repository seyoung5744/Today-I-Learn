package com.example.springbootbatchsample.application.job;

import com.example.springbootbatchsample.application.model.ArticleModel;
import com.example.springbootbatchsample.domain.entity.Article;
import com.example.springbootbatchsample.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CreateArticleJobConfig {

    private final ArticleRepository articleRepository;
    private final JdbcTemplate jdbcTemplate;
    
    @Bean
    public Job createArticleJob(JobRepository jobRepository, Step createArticleStep) {
        return new JobBuilder("createArticleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(createArticleStep)
                .build();
    }

    @Bean
    public Step createArticleStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("createArticleStep", jobRepository)
                .<ArticleModel, Article>chunk(100, platformTransactionManager)
                .reader(createArticleReader())
                .processor(createArticleProcessor())
                .writer(createArticleWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<ArticleModel> createArticleReader() {
        return new FlatFileItemReaderBuilder<ArticleModel>()
                .name("createArticleReader")
                .resource(new ClassPathResource("Articles.csv"))
                .delimited()
                .names("title", "content")
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ArticleModel.class)
                .beanMapperStrict(true)
                .build();
    }

    @Bean
    public ItemProcessor<ArticleModel, Article> createArticleProcessor() {
        return articleModel -> Article.builder()
                .title(articleModel.getTitle())
                .content(articleModel.getContent())
                .build();
    }

    @Bean
    public RepositoryItemWriter<Article> createArticleWriter() {
        return new RepositoryItemWriterBuilder<Article>()
                .repository(articleRepository)
                .build();
    }
}
