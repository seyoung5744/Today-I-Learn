package com.example.springbootbatchsample.application.job;

import com.example.springbootbatchsample.application.job.param.HardDeleteBoardJobParam;
import com.example.springbootbatchsample.domain.entity.Board;
import com.example.springbootbatchsample.domain.entity.DeletedBoard;
import com.example.springbootbatchsample.domain.repository.BoardRepository;
import com.example.springbootbatchsample.domain.repository.DeletedBoardRepository;
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

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HardDeleteBoardJobConfig {

    private static final int CHUNK_SIZE = 1000;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final BoardRepository boardRepository;
    private final DeletedBoardRepository deletedBoardRepository;
    private final HardDeleteBoardJobParam hardDeleteBoardJobParam;

    @Bean
    public Job hardDeleteBoardJob() {
        return new JobBuilder("hardDeleteBoardJob", jobRepository)
                .start(hardDeleteBoardStep())
                .build();
    }

    @Bean
    public Step hardDeleteBoardStep() {
        return new StepBuilder("hardDeleteBoardStep", jobRepository)
                .<Board, DeletedBoard>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(hardDeleteBoardReader())
                .processor(hardDeleteBoardProcessor())
                .writer(hardDeleteBoardWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Board> hardDeleteBoardReader() {
        return new RepositoryItemReaderBuilder<Board>()
                .name("hardDeleteBoardReader")
                .repository(boardRepository)
                .methodName("findAllByCreatedAtBefore")
                .arguments(hardDeleteBoardJobParam.getCreatedAt())
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Board, DeletedBoard> hardDeleteBoardProcessor() {
        LocalDateTime now = LocalDateTime.now();
        return board -> DeletedBoard.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .isDeleted(board.isDeleted())
                .createdAt(board.getCreatedAt())
                .deletedAt(now)
                .build();
    }

    @Bean
    public RepositoryItemWriter<DeletedBoard> hardDeleteBoardWriter() {
        return new RepositoryItemWriterBuilder<DeletedBoard>()
                .repository(deletedBoardRepository)
                .build();
    }
}
