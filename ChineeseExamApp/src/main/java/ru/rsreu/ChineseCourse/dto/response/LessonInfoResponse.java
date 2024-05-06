package ru.rsreu.ChineseCourse.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.ResourceMaterial;
import ru.rsreu.ChineseCourse.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LessonInfoResponse {
    private Long id;
    private String name;
    private String description;
    private Long courseId;
    private List<ResourceMaterialInfoResponse> resourceMaterialList = new ArrayList<>();
    private List<QuestionInfoResponse> questions = new ArrayList<>();


    public static LessonInfoResponse fromLesson(Lesson lesson){
        return LessonInfoResponse.builder()
                .id(lesson.getId())
                .name(lesson.getLessonName())
                .description(lesson.getDescription())
                .resourceMaterialList(lesson.getResourceMaterialList()
                        .stream().map(ResourceMaterialInfoResponse::fromResourceMaterial).toList())
                //.questions(lesson.getQuestions().stream().map(QuestionInfoResponse::fromQuestion).toList())
                .courseId(lesson.getCourse().getId())
                .build();
    }


}