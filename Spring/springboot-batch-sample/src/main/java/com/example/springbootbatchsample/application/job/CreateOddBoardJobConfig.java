package com.example.springbootbatchsample.application.job;

import com.example.springbootbatchsample.application.job.param.CreateOddBoardJobParam;
import com.example.springbootbatchsample.domain.entity.Board;
import com.example.springbootbatchsample.domain.entity.OddBoard;
import com.example.springbootbatchsample.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CreateOddBoardJobConfig {

    private static final int CHUNK_SIZE = 1000;
    private static final int POOL_SIZE = 5;
    private static final int GRID_SIZE = 10;

    private final CreateOddBoardJobParam createOddBoardJobParam;
    private final JdbcTemplate testJdbcTemplate;
    private final BoardRepository boardRepository;

    @Bean
    public Job createOddBoardJob(JobRepository jobRepository, Step createOddBoardManager) {
        return new JobBuilder("createOddBoardJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(createOddBoardManager)
                .build();
    }

    @Bean
    public Step createOddBoardManager(JobRepository jobRepository, Step createOddBoardWorker) {
        return new StepBuilder("createOddBoardManager", jobRepository)
                .partitioner("createOddBoardPartitioner", createOddBoardPartitioner())
                .partitionHandler(createOddBoardPartitionHandler(createOddBoardWorker))
                .build();
    }

    @Bean
    @StepScope
    public Partitioner createOddBoardPartitioner() {
        return gridSize -> {
            long min = createOddBoardJobParam.getMinId();
            long max = createOddBoardJobParam.getMaxId();
            long targetSize = (max - min) / gridSize + 1;

            Map<String, ExecutionContext> result = new HashMap<>();
            int number = 0;
            long start = min;
            long end = start + targetSize - 1;

            while (start <= max) {
                ExecutionContext value = new ExecutionContext();
                result.put("partition" + number, value);

                if (end >= max) {
                    end = max;
                }
                value.putLong("minValue", start);
                value.putLong("maxValue", end);
                start += targetSize;
                end += targetSize;
                number++;
            }

            return result;
        };
    }

    @Bean
    public ThreadPoolTaskExecutor createOddBoardTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(POOL_SIZE);
        taskExecutor.setMaxPoolSize(POOL_SIZE);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    public TaskExecutorPartitionHandler createOddBoardPartitionHandler(Step createOddBoardWorker) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(createOddBoardWorker); // worker setting
        partitionHandler.setGridSize(GRID_SIZE);
        partitionHandler.setTaskExecutor(createOddBoardTaskExecutor());
        return partitionHandler;
    }

    @Bean
    public Step createOddBoardWorker(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("createOddBoardWorker", jobRepository)
                .<Board, OddBoard>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(createOddBoardReader(null, null))
                .processor(createOddBoardProcessor())
                .writer(createOddBoardWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Board> createOddBoardReader(
            @Value("#{stepExecutionContext[minValue]}") Long minValue,
            @Value("#{stepExecutionContext[maxValue]}") Long maxValue
    ) {
        return new RepositoryItemReaderBuilder<Board>()
                .name("createBoardReader")
                .repository(boardRepository)
                .methodName("findAllByIdBetween")
                .arguments(minValue, maxValue)
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Board, OddBoard> createOddBoardProcessor() {
        LocalDateTime now = LocalDateTime.now();
        return board -> {
            if (board.getId() % 2 != 0) {
                return OddBoard.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(now)
                        .build();
            } else {
                return null;
            }
        };
    }

    @Bean
    public ItemWriter<OddBoard> createOddBoardWriter() {
        return boards -> testJdbcTemplate.batchUpdate("insert into odd_board (id, title, content, created_at) values (?,?,?,?)",
                boards.getItems(),
                CHUNK_SIZE,
                (ps, board) -> {
                    ps.setObject(1, board.getId());
                    ps.setObject(2, board.getTitle());
                    ps.setObject(3, board.getContent());
                    ps.setObject(4, board.getCreatedAt());
                }
        );
    }
}
