package ru.rsreu.ChineseCourse.dto.response;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseInfoResponse {

    private Long id;
    private String courseName;

    private String description;

    private CourseDifficulty difficulty;

    private String admin;

    public static CourseInfoResponse fromCourse(Course course){
        return CourseInfoResponse.builder()
                .admin(course.getAdmin().getEmail())
                .courseName(course.getCourseName())
                .difficulty(course.getDifficulty())
                .id(course.getId())
                .description(course.getDescription())
                .build();
    }
}
