package ru.rsreu.ChineseCourse.service;

import jakarta.transaction.Transactional;
import ru.rsreu.ChineseCourse.dto.LessonInfoDto;
import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.User;

import java.util.List;

public interface ILessonService {

    Lesson findById(Long id);

    void logMaterial(Long id, User user);

    Lesson createLesson(User admin, LessonInfoDto lessonRequest);

    @Transactional
    Lesson updateLesson(User admin, LessonInfoDto lessonRequest);

    @Transactional
    Lesson deleteLessonById(Long lessonId);
}
