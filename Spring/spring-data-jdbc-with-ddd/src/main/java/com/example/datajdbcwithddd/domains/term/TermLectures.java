package com.example.datajdbcwithddd.domains.term;

public record TermLectures(Long lecture) {

    public static TermLectures of(Long lecture) {
        return new TermLectures(lecture);
    }
}
