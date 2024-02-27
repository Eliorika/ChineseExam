package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {


}
