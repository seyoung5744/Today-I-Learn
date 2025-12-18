package com.example.datajdbcwithddd.infrastructure.jdbc;

import com.example.datajdbcwithddd.domains.term.Term;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public final class Converts {

    @WritingConverter
    public enum TermToString implements Converter<Term, String> {
        INSTANCE;

        @Override
        public String convert(Term source) {
            return source.name();
        }
    }

    @ReadingConverter
    public enum StringToTerm implements Converter<String, Term> {
        INSTANCE;

        @Override
        public Term convert(String source) {
            return Term.of(source);
        }
    }
}
