package com.example.datajdbcwithddd.domains.students;

import com.example.datajdbcwithddd.domains.registration.Registration;
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
public class Student {

    @Id
    private StudentId id;

    private String name;

    private final Set<Registration> registrations;

    public static Student of(String name) {
        return new Student(null, name, new HashSet<>());
    }

    public void register(Registration registration) {
        if (registration.lecture() != null) {
            registrations.add(registration);
        }
    }
}
