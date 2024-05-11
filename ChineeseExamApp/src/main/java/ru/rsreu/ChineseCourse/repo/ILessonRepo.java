package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.AnswerStatistic;
import ru.rsreu.ChineseCourse.model.Lesson;

import java.util.Optional;

@Repository
public interface ILessonRepo extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findByLessonName(String lessonName);


}
