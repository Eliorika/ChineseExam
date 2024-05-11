package ru.rsreu.ChineseCourse.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.Test;
import ru.rsreu.ChineseCourse.model.Topic;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.Variant;
import ru.rsreu.ChineseCourse.model.enums.QuestionType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoDto {

    private Long id;
    private String question;
    private String cardText;
    private QuestionType questionType;
    private String answer;
    private boolean isTest;
    private boolean isGenerated;
    private List<Variant> variants;
    private Long testId;
    private Topic topic;

}
