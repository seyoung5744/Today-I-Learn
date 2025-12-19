package com.example.datajdbcwithddd.application.listener;

import com.example.datajdbcwithddd.domains.lecture.CreateLecture;
import com.example.datajdbcwithddd.domains.lecture.Lecture;
import com.example.datajdbcwithddd.domains.lecture.LectureRepository;
import com.example.datajdbcwithddd.domains.term.LectureAdded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LectureAddListener {

    @Autowired
    LectureRepository lectureRepository;

    @Async
    @EventListener
    public void on(LectureAdded anEvent) {
        CreateLecture command = new CreateLecture(anEvent.lectureId(), anEvent.termId(), anEvent.name());

        Lecture lecture = Lecture.create(command);
        lectureRepository.save(lecture);
    }
}
