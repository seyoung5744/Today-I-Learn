package com.example.datajdbcwithddd.config;

import com.example.datajdbcwithddd.infrastructure.jdbc.Converts;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.List;

@Configuration
public class DataJdbcConfig extends AbstractJdbcConfiguration {

    @Override
    protected List<?> userConverters() {
        return List.of(
//                Converts.StringToTerm.INSTANCE,
//                Converts.TermToString.INSTANCE
                Converts.LectureIdToLong.INSTANCE,
                Converts.LongToLectureId.INSTANCE,
                Converts.TermIdToLong.INSTANCE,
                Converts.LongToTermId.INSTANCE
        );
    }
}
