package com.example.datajdbcwithddd.domains.lecture;


import com.example.datajdbcwithddd.domains.term.Term;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceCreator)
public class Lecture {

    @Id
    private Long id;

    private final String name;
    private final Term term;

    @Deprecated
    public static Lecture create(String name, String term) {
        return create(name, Term.of(term));
    }

    public static Lecture create(String name, Term term) {
        return new Lecture(name, term);
    }
}
