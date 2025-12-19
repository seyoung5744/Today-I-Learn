package com.example.datajdbcwithddd.domains.registration;

import com.example.datajdbcwithddd.domains.lecture.LectureId;
import com.example.datajdbcwithddd.domains.students.StudentId;

public record Registration(
        LectureId lecture,
        StudentId student
) {

    public static Registration of(LectureId lectureId, StudentId studentId) {
        return new Registration(lectureId, studentId);
    }
}
