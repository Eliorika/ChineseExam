package ru.rsreu.ChineseCourse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;

@Getter
@Setter
@NoArgsConstructor
public class CourseInfoRequest {

    private Long id;

    @NotBlank
    private String courseName;

    @NotBlank
    private String description;

    @NotNull
    private CourseDifficulty difficulty;

}
