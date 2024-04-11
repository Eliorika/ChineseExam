package ru.rsreu.ChineseCourse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.dto.response.student.CourseInfoStudentResponse;
import ru.rsreu.ChineseCourse.dto.response.student.LessonInfoStudentResponse;
import ru.rsreu.ChineseCourse.dto.response.student.TestInfoStudentResponse;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.Test;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.repo.ICourseRepo;
import ru.rsreu.ChineseCourse.repo.ITestRepo;
import ru.rsreu.ChineseCourse.service.ICourseService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements ICourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final ICourseRepo courseRepo;
    private final ITestRepo testRepo;

    @Override
    @Transactional
    public Course createCourse(CourseInfoRequest req, User user) {
        Course course = new Course();
        course.setCourseName(req.getCourseName());
        course.setDescription(req.getDescription());
        course.setAdmin(user);
        course.setDifficulty(req.getDifficulty());
        course.setStudents(new ArrayList<>());

        return courseRepo.save(course);
    }

    @Override
    @Transactional
    public Course updateCourse(CourseInfoRequest req, Long id) {
        Course course = courseRepo.findById(id).orElse(null);
        if (course == null) {
            log.debug("Course " + req.getCourseName() + " not found");
            throw new NotFoundException("Course with id " + id + " not found");
        }
        course.setCourseName(req.getCourseName());
        course.setDescription(req.getDescription());
        course.setDifficulty(req.getDifficulty());

        return courseRepo.save(course);
    }

    @Override
    @Transactional
    public Course deleteCourse(Long id) {
        Course course = courseRepo.findById(id).orElse(null);
        if (course == null) {
            log.debug("Course with id " + id + " not found");
            throw new NotFoundException("Course with id " + id + " not found");
        }
        courseRepo.delete(course);
        return course;
    }

    @Override
    public List<Course> allCourses() {
        var all = courseRepo.findAll();
        return all;
    }

    @Override
    public Course getById(Long id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Course addBlankLesson(Long courseId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        Lesson lesson = new Lesson();
        return null;
    }

    @Override
    public CourseInfoStudentResponse getStudentCourseInfo(Long courseId, Long userId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) {
            log.debug("Course with id " + courseId + " not found");
            throw new NotFoundException("Course with id " + courseId + " not found");
        }

        CourseInfoStudentResponse response = CourseInfoStudentResponse.fromCourseBase(course);

        for (Lesson lesson : course.getLessons()) {
            if (lesson.getIsVisible()) {
                LessonInfoStudentResponse lessonInfoStudentResponse = LessonInfoStudentResponse.fromLessonBase(lesson);
                boolean isAvailable = true;
                for (Test test : lesson.getTests()) {
                    if (test.getIsVisible()) {
                        boolean isPassed = testRepo.isTestCompletedByUser(test.getId(), userId);
                        lessonInfoStudentResponse.getTests()
                                .add(new TestInfoStudentResponse(test.getId(), isPassed, isAvailable));
                        if (!isPassed) {
                            isAvailable = false;
                        }
                    }
                }
                response.getLessons().add(lessonInfoStudentResponse);
            }
        }

        return response;
    }
}
