package com.example.datajdbcwithddd.domains.term;

import com.example.datajdbcwithddd.domains.lecture.LectureId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceCreator)
public class Term {

    @Id
    private Long id;
    private String name;
    private int limitOfLecture;

    private Set<TermLectures> lectures;

    public void add(AddLecture command) {
        if (lectures.size() >= limitOfLecture) {
            throw new IllegalStateException("학기의 최대 개설 강의 수를 초과하였습니다.");
        }
        LectureId aLectureID = LectureId.of(1L);
        lectures.add(TermLectures.of(aLectureID));
    }

    @Deprecated
    public static Term of(String name) {
        return new Term(null, name, 10, new HashSet<>());
    }

    public static Term of(String name, int limitOfLecture) {
        return new Term(null, name, limitOfLecture, new HashSet<>());
    }
}
