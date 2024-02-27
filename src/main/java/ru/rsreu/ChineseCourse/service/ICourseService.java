package ru.rsreu.ChineseCourse.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rsreu.ChineseCourse.dto.request.CreateCourseRequest;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;

public interface ICourseService {

    Course createCourse(CreateCourseRequest req, User user);
}
