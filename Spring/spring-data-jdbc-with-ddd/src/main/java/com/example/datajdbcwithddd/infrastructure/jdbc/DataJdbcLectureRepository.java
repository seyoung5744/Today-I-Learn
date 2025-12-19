package com.example.datajdbcwithddd.infrastructure.jdbc;

import com.example.datajdbcwithddd.domains.lecture.Lecture;
import com.example.datajdbcwithddd.domains.lecture.LectureId;
import com.example.datajdbcwithddd.domains.lecture.LectureRepository;
import org.springframework.data.repository.Repository;

public interface DataJdbcLectureRepository extends LectureRepository, Repository<Lecture, LectureId> {
}
