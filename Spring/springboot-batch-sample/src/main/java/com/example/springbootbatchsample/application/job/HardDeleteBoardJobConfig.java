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
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;
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
                .start(backupBoardStep())
                .next(hardDeleteBoardStep())
                .build();
    }

    @Bean
    public Step backupBoardStep() {
        return new StepBuilder("hardDeleteBoardStep", jobRepository)
                .<Board, DeletedBoard>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(backupBoardReader())
                .processor(backupBoardProcessor())
                .writer(backupBoardWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Board> backupBoardReader() {
        return new RepositoryItemReaderBuilder<Board>()
                .name("backupBoardReader")
                .repository(boardRepository)
                .methodName("findAllByCreatedAtBefore")
                .arguments(hardDeleteBoardJobParam.getCreatedAt())
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Board, DeletedBoard> backupBoardProcessor() {
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
    public ItemWriter<DeletedBoard> backupBoardWriter() {
        return new ItemWriter<DeletedBoard>() {

            private StepExecution stepExecution;

            @Override
            public void write(Chunk<? extends DeletedBoard> deletedBoardChunk) throws Exception {
                List<? extends DeletedBoard> targetBoards = deletedBoardChunk.getItems();
                List<Long> deleteBoardIds = deletedBoardRepository.saveAll(targetBoards).stream()
                        .map(DeletedBoard::getId)
                        .toList();
                ExecutionContext executionContext = this.stepExecution.getJobExecution().getExecutionContext();
                executionContext.put("deleteBoardIds", deleteBoardIds);
            }

            @BeforeStep
            public void setStepExecution(final StepExecution stepExecution) {
                this.stepExecution = stepExecution;
            }
        };
    }

    @Bean
    @JobScope
    public Step hardDeleteBoardStep() {
        return new StepBuilder("hardDeleteBoardStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> jobExecutionContext = chunkContext.getStepContext().getJobExecutionContext();
                    List<Long> deleteBoardIds = (List<Long>) jobExecutionContext.get("deleteBoardIds");
                    boardRepository.deleteAllByIdIn(deleteBoardIds);
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }
}
