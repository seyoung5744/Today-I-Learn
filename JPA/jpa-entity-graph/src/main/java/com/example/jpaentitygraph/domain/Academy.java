package com.example.jpaentitygraph.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Academy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL)
    private List<Subject> subjects = new ArrayList<>();

    @Builder
    public Academy(String name, List<Subject> subjects) {
        this.name = name;
        if (subjects != null) {
            this.subjects = subjects;
        }
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.updateAcademy(this);
    }
}
