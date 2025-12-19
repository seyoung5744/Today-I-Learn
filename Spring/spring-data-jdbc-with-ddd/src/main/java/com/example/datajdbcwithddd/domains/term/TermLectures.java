package com.example.datajdbcwithddd.domains.term;

import com.example.datajdbcwithddd.domains.lecture.LectureId;

public record TermLectures(
        LectureId lecture
) {

    public static TermLectures of(LectureId lecture) {
        return new TermLectures(lecture);
    }
}
