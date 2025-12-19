package com.example.datajdbcwithddd.domains.term;

import com.example.datajdbcwithddd.domains.lecture.LectureId;

import java.time.Year;

public record TermId(Long id) {

    @Deprecated
    public static TermId of(String name) {
        String[] splited = name.split("-");
        if (splited.length != 2) {
            throw new IllegalArgumentException();
        }

        return TermId.of(
                Year.of(Integer.parseInt(splited[0])),
                Qtr.of(Integer.parseInt(splited[1])));
    }

    @Deprecated
    public static TermId of(Long id) {
        return new TermId(id);
    }

    public static TermId of(Year year, Qtr qtr) {
        return new TermId((year.getValue() * 10L) + qtr.value);
    }

    public Long getValue() {
        return id;
    }

    public LectureId createLectureId(int idx) {
        return LectureId.of(id * 100 + (idx + 1));
    }

    @Override
    public String toString() {
        long year = getValue() / 10;
        long qtr = getValue() % 10;
        return String.format("Term %d-%d", year, qtr);
    }
}
