package com.example.datajdbcwithddd.infrastructure.jdbc;

import com.example.datajdbcwithddd.domains.lecture.LectureId;
import com.example.datajdbcwithddd.domains.students.StudentId;
import com.example.datajdbcwithddd.domains.term.TermId;
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

    @WritingConverter
    public enum TermIdToLong implements Converter<TermId, Long> {
        INSTANCE;

        @Override
        public Long convert(TermId source) {
            return source.id();
        }
    }

    @ReadingConverter
    public enum LongToTermId implements Converter<Long, TermId> {
        INSTANCE;

        @Override
        public TermId convert(Long source) {
            return TermId.of(source);
        }
    }

    @WritingConverter
    public enum StudentIdToLong implements Converter<StudentId, Long> {
        INSTANCE;

        @Override
        public Long convert(StudentId source) {
            return source.id();
        }
    }

    @ReadingConverter
    public enum LongToStudentId implements Converter<Long, StudentId> {
        INSTANCE;

        @Override
        public StudentId convert(Long source) {
            return StudentId.of(source);
        }
    }

}
