package com.example.datajdbcwithddd.domains.students;

public record StudentId(
        Long id
) {
    public static StudentId of(Long id) {
        return new StudentId(id);
    }
}
