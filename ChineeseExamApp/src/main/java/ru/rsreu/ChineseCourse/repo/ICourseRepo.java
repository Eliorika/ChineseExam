package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.Course;

@Repository
public interface ICourseRepo extends JpaRepository<Course, Long> {


}
