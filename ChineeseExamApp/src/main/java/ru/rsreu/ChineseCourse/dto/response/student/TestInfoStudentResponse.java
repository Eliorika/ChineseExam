package ru.rsreu.ChineseCourse.dto.response.student;

import lombok.*;
import ru.rsreu.ChineseCourse.dto.response.ResourceMaterialInfoResponse;
import ru.rsreu.ChineseCourse.model.Lesson;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TestInfoStudentResponse {
    private Long id;
    private Boolean isPassed;
    private Boolean isAvailable;

}