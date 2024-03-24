package ru.rsreu.ChineseCourse.service;

import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;

import java.util.List;

public interface ICourseService {

    Course createCourse(CourseInfoRequest req, User user);
    Course updateCourse(CourseInfoRequest req, Long id);
    Course deleteCourse(Long id);

    List<Course> allCourses();

    Course getById(Long id);
}
