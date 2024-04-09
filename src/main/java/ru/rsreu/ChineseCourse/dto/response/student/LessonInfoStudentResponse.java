package ru.rsreu.ChineseCourse.dto.response.student;

import lombok.*;
import ru.rsreu.ChineseCourse.dto.response.ResourceMaterialInfoResponse;
import ru.rsreu.ChineseCourse.model.Lesson;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LessonInfoStudentResponse {
    private Long id;
    private String name;
    private String description;
    private Long courseId;
    private List<ResourceMaterialInfoResponse> resourceMaterialList;
    private List<TestInfoStudentResponse> tests = new ArrayList<>();


    public static LessonInfoStudentResponse fromLessonBase(Lesson lesson){
        return LessonInfoStudentResponse.builder()
                .id(lesson.getId())
                .name(lesson.getLessonName())
                .description(lesson.getDescription())
                .resourceMaterialList(lesson.getResourceMaterialList()
                        .stream().map(ResourceMaterialInfoResponse::fromResourceMaterial).toList())
                .courseId(lesson.getCourse().getId())
                .tests(new ArrayList<>())
                .build();
    }


}