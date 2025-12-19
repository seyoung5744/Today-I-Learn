package com.example.datajdbcwithddd.domains.lecture;


import com.example.datajdbcwithddd.domains.registration.Registration;
import com.example.datajdbcwithddd.domains.students.Student;
import com.example.datajdbcwithddd.domains.term.TermId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceCreator)
public class Lecture {

    @Id
    private LectureId id;

    @Version
    private Long version;

    private final String name;
    private final TermId termId;

    private final Set<Registration> registrations;

    public void register(Student student) {
        Registration registration = Registration.of(id, student.getId());
        registrations.add(registration);
        student.register(registration);
    }

    public static Lecture create(CreateLecture command) {
        return new Lecture(command.lectureId(), null, command.name(), command.termId(), new HashSet<>());
    }
}
