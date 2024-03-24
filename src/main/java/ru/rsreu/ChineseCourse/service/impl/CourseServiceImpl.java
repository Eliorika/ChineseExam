package ru.rsreu.ChineseCourse.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rsreu.ChineseCourse.dto.request.CourseInfoRequest;
import ru.rsreu.ChineseCourse.exception.NotFoundException;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.repo.CourseRepo;
import ru.rsreu.ChineseCourse.service.ICourseService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements ICourseService {

    private final CourseRepo courseRepo;
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Override
    public Course createCourse(CourseInfoRequest req, User user){
        Course course = new Course();
        course.setCourseName(req.getCourseName());
        course.setDescription(req.getDescription());
        course.setAdmin(user);
        course.setDifficulty(req.getDifficulty());
        course.setStudents(new ArrayList<>());

        return courseRepo.save(course);
    }

    @Override
    public Course updateCourse(CourseInfoRequest req, Long id) {
        Course course = courseRepo.findById(id).orElse(null);
        if(course == null){
            log.debug("Course " + req.getCourseName() + " not found");
            throw new NotFoundException("Course with id " + id + " not found");
        }
        course.setCourseName(req.getCourseName());
        course.setDescription(req.getDescription());
        course.setDifficulty(req.getDifficulty());

        return courseRepo.save(course);
    }

    @Override
    public Course deleteCourse(Long id) {
        Course course = courseRepo.findById(id).orElse(null);
        if(course == null){
            log.debug("Course with id " + id + " not found");
            throw new NotFoundException("Course with id " + id + " not found");
        }
        courseRepo.delete(course);
        return course;
    }

    @Override
    public List<Course> allCourses(){
        var all = courseRepo.findAll();
        return all;
    }

    @Override
    public Course getById(Long id){
        return courseRepo.findById(id).orElse(null);
    }
}
