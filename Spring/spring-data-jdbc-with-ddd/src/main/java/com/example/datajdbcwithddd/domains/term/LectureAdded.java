package com.example.datajdbcwithddd.domains.term;

import com.example.datajdbcwithddd.domains.lecture.LectureId;

public record LectureAdded(
        String name,
        LectureId lectureId,
        TermId termId
) {

    public static LectureAdded create(AddLecture command, LectureId lectureId, TermId termId) {
        return new LectureAdded(command.name(), lectureId, termId);
    }
}
