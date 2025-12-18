package com.example.datajdbcwithddd.infrastructure.jdbc;

import com.example.datajdbcwithddd.domains.lecture.LectureId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public final class Converts {

//    @WritingConverter
//    public enum TermToString implements Converter<Term, String> {
//        INSTANCE;
//
//        @Override
//        public String convert(Term source) {
//            return source.getName();
//        }
//    }
//
//    @ReadingConverter
//    public enum StringToTerm implements Converter<String, Term> {
//        INSTANCE;
//
//        @Override
//        public Term convert(String source) {
//            return Term.of(source);
//        }
//    }

    @WritingConverter
    public enum LectureIdToLong implements Converter<LectureId, Long> {
        INSTANCE;

        @Override
        public Long convert(LectureId source) {
            return source.id();
        }
    }

    @ReadingConverter
    public enum LongToLectureId implements Converter<Long, LectureId> {
        INSTANCE;

        @Override
        public LectureId convert(Long source) {
            return LectureId.of(source);
        }
    }

}
