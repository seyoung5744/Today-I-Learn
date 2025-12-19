package com.example.datajdbcwithddd.domains.lecture;

public interface LectureRepository {

    Lecture save(Lecture aLecture);

    Lecture findById(LectureId id);
}
