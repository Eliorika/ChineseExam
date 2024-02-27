package ru.rsreu.ChineseCourse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.request.CreateCourseRequest;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.repo.CourseRepo;
import ru.rsreu.ChineseCourse.service.ICourseService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final CourseRepo courseRepo;

    @Override
    public Course createCourse(CreateCourseRequest req, User user){
        Course course = new Course();
        course.setCourseName(req.getCourseName());
        course.setDescription(req.getCourseName());
        course.setAdmin(user);
        course.setDifficulty(req.getDifficulty());
        course.setStudents(new ArrayList<>());

        return courseRepo.save(course);
    }
}
