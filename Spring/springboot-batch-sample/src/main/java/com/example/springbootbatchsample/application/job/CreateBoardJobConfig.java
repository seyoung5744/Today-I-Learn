package com.example.springbootbatchsample.application.job;

import com.example.springbootbatchsample.application.model.BoardModel;
import com.example.springbootbatchsample.domain.entity.Board;
import com.example.springbootbatchsample.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CreateBoardJobConfig {

    private static final int CHUNK_SIZE = 1000;
    private static final int POOL_SIZE = 5;
    private static final int GRID_SIZE = 10;

    private final JdbcTemplate testJdbcTemplate;

    @Bean
    public Job createBoardJob(JobRepository jobRepository, Step createBoardManager) {
        return new JobBuilder("createBoardJob", jobRepository)
                .start(createBoardManager)
                .build();
    }

    @Bean
    public Step createBoardManager(JobRepository jobRepository, Step createBoardWorker) {
        return new StepBuilder("createBoardManager", jobRepository)
                .partitioner("createBoardPartitioner", createBoardPartitioner())
                .partitionHandler(createBoardPartitionHandler(createBoardWorker))
                .build();
    }

    @Bean
    @StepScope
    public Partitioner createBoardPartitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();

        Path path = Paths.get("C:/Users/seyou/Desktop/my_Git_Hub/Today-I-Learn/Spring/springboot-batch-sample/storage");
        Resource[] resources = FileUtils.stream(path)
                .filter(File::isFile)
                .filter(file -> "csv".equals(StringUtils.getFilenameExtension(file.getPath())))
                .map(FileSystemResource::new)
                .toArray(Resource[]::new);

        partitioner.setResources(resources);
        partitioner.partition(GRID_SIZE);
        return partitioner;
    }

    @Bean
    public ThreadPoolTaskExecutor createBoardTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(POOL_SIZE);
        taskExecutor.setMaxPoolSize(POOL_SIZE);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    public TaskExecutorPartitionHandler createBoardPartitionHandler(Step createBoardWorker) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(createBoardWorker); // worker setting
        partitionHandler.setGridSize(GRID_SIZE);
        partitionHandler.setTaskExecutor(createBoardTaskExecutor());
        return partitionHandler;
    }

    // worker : 파일을 읽어서 데이터 처리
    @Bean
    public Step createBoardWorker(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws MalformedURLException {
        return new StepBuilder("createBoardWorker", jobRepository)
                .<BoardModel, Board>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(createBoardReader(null))
                .processor(createBoardProcessor())
                .writer(createBoardWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<BoardModel> createBoardReader(@Value("#{stepExecutionContext[fileName]}") String fileName) throws MalformedURLException {
        return new FlatFileItemReaderBuilder<BoardModel>()
                .name("createBoardReader")
                .resource(new UrlResource(fileName))
                .delimited()
                .names("title", "content")
                .targetType(BoardModel.class)
                .beanMapperStrict(true)
                .build();
    }

    @Bean
    public ItemProcessor<BoardModel, Board> createBoardProcessor() {
        LocalDateTime now = LocalDateTime.now();
        return boardModel -> Board.builder()
                .title(boardModel.getTitle())
                .content(boardModel.getContent())
                .createdAt(now)
                .build();
    }

    @Bean
    public ItemWriter<Board> createBoardWriter() {
        return boards -> testJdbcTemplate.batchUpdate("insert into board (title,content,created_at) values (?,?,?)",
                boards.getItems(),
                CHUNK_SIZE,
                (ps, board) -> {
                    ps.setObject(1, board.getTitle());
                    ps.setObject(2, board.getContent());
                    ps.setObject(3, board.getCreatedAt());
                }
        );

    }
}
