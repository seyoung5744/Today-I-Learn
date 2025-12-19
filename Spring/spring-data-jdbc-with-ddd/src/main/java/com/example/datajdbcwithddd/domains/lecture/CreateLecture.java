package com.example.datajdbcwithddd.domains.lecture;

import com.example.datajdbcwithddd.domains.term.TermId;

public record CreateLecture(
        LectureId lectureId, TermId termId, String name
) {
}
