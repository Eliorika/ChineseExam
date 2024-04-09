package ru.rsreu.ChineseCourse.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.Question;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.Variant;
import ru.rsreu.ChineseCourse.model.enums.QuestionType;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionsDto {
    private Long questionId;
    private String question;
    private String answer = "";
    private String rightAnswer = "";
    private String cardText;
    private List<String> variants;
    private QuestionType questionType;
    private Long testId;
    private Boolean correctness;
    //private boolean isTest;

    public static TestQuestionsDto fromQuestion(Question q){
        return TestQuestionsDto.builder()
                .questionId(q.getId())
                .question(q.getQuestion())
                .answer("")
                .rightAnswer("")
                .cardText(q.getCardText())
                .variants(q.getVariants().stream().map(v -> (v.getVariant())).toList())
                .questionType(q.getQuestionType())
                .testId(q.getTest().getId())
                .build();
    }

}
