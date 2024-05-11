package ru.rsreu.ChineseCourse.dto;

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
public class LessonInfoDto {
    private Long id;
    private String name;
    private String description;
    private Long courseId;
    private List<ResourceMaterialInfoResponse> resourceMaterialList = new ArrayList<>();
    private boolean isVisible;


    public static LessonInfoDto fromLesson(Lesson lesson){
        return LessonInfoDto.builder()
                .id(lesson.getId())
                .name(lesson.getLessonName())
                .description(lesson.getDescription())
                .resourceMaterialList(lesson.getResourceMaterialList()
                        .stream().map(ResourceMaterialInfoResponse::fromResourceMaterial).toList())
                .courseId(lesson.getCourse().getId())
                .isVisible(lesson.getIsVisible())
                .build();
    }


}