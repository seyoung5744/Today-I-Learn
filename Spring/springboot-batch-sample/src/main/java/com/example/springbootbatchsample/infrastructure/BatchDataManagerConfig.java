package com.example.springbootbatchsample.infrastructure;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchDataManagerConfig extends DefaultBatchConfiguration {

    private final PlatformTransactionManager batchTransactionManager;

    public BatchDataManagerConfig(
            @Qualifier("batchTransactionManager") PlatformTransactionManager batchTransactionManager) {
        this.batchTransactionManager = batchTransactionManager;
    }

    @Override
    protected PlatformTransactionManager getTransactionManager() {
        return this.batchTransactionManager;
    }

}
