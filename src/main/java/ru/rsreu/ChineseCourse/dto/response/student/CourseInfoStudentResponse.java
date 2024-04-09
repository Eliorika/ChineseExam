package ru.rsreu.ChineseCourse.dto.response.student;


import lombok.*;
import ru.rsreu.ChineseCourse.dto.response.LessonInfoResponse;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseInfoStudentResponse {

    private Long id;
    private String courseName;
    private String description;
    private CourseDifficulty difficulty;
    private List<LessonInfoStudentResponse> lessons = new ArrayList<>();

    public static CourseInfoStudentResponse fromCourseBase(Course course){
        return CourseInfoStudentResponse.builder()
                .courseName(course.getCourseName())
                .difficulty(course.getDifficulty())
                .id(course.getId())
                .description(course.getDescription())
                .lessons(new ArrayList<>())
                .build();
    }
}
