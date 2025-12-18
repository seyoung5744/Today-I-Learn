package com.example.datajdbcwithddd.domains.term;

public record Term(String name) {

    public static Term of(String name) {
        return new Term(name);
    }
}
