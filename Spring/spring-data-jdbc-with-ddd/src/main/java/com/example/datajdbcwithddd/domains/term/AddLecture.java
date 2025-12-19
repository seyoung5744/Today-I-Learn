package com.example.datajdbcwithddd.domains.term;

public record AddLecture(String name) {

    public static AddLecture of(String name) {
        return new AddLecture(name);
    }
}
