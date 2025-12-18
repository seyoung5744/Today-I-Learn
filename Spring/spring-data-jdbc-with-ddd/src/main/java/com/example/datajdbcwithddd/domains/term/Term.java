package com.example.datajdbcwithddd.domains.term;

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

    private Set<TermLectures> lectures;

    public static Term of(String name) {
        return new Term(null, name, new HashSet<>());
    }

    public void add(Long aLectureID) {
        lectures.add(TermLectures.of(aLectureID));
    }
}
