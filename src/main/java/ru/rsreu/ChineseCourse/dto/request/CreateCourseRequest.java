package ru.rsreu.ChineseCourse.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateCourseRequest {

    @NotBlank
    private String courseName;

    @NotBlank
    private String description;

    @NotNull
    private CourseDifficulty difficulty;

}
