package com.example.springbootbatchsample.application.job;

import com.example.springbootbatchsample.application.job.param.SoftDeleteBoardJobParam;
import com.example.springbootbatchsample.domain.entity.Board;
import com.example.springbootbatchsample.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SoftDeleteBoardJobConfig {

    private static final int CHUNK_SIZE = 1000;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final BoardRepository boardRepository;
    private final SoftDeleteBoardJobParam softDeleteBoardJobParam;

    @Bean
    public Job softDeleteBoardJob() {
        return new JobBuilder("softDeleteBoardJob", jobRepository)
                .start(softDeleteBoardStep())
                .build();
    }

    @Bean
    public Step softDeleteBoardStep() {
        return new StepBuilder("softDeleteBoardStep", jobRepository)
                .<Board, Board>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(softDeleteBoardReader())
                .processor(softDeleteBoardProcessor())
                .writer(softDeleteBoardWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Board> softDeleteBoardReader() {
        return new RepositoryItemReaderBuilder<Board>()
                .name("softDeleteBoardReader")
                .repository(boardRepository)
                .methodName("findAllByCreatedAtBefore")
                .arguments(softDeleteBoardJobParam.getCreatedAt())
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Board, Board> softDeleteBoardProcessor() {
        return board -> {
            board.setDeleted(true);
            return board;
        };
    }

    @Bean
    public RepositoryItemWriter<Board> softDeleteBoardWriter() {
        return new RepositoryItemWriterBuilder<Board>()
                .repository(boardRepository)
                .build();
    }
}
