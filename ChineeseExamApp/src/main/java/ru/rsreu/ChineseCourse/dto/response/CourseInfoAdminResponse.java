package ru.rsreu.ChineseCourse.dto.response;


import lombok.*;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseInfoAdminResponse {

    private Long id;
    private String courseName;

    private String description;

    private CourseDifficulty difficulty;

    private String admin;

    public static CourseInfoAdminResponse fromCourse(Course course){
        return CourseInfoAdminResponse.builder()
                .admin(course.getAdmin().getEmail())
                .courseName(course.getCourseName())
                .difficulty(course.getDifficulty())
                .id(course.getId())
                .description(course.getDescription())
                .build();
    }
}
