package com.example.datajdbcwithddd.domains.lecture;

public record LectureId(Long id) {

    public static LectureId of(Long id) {
        return new LectureId(id);
    }
}
