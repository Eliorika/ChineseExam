package ru.rsreu.ChineseCourse.dto.response;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.Question;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.Variant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionInfoResponse {
    private Long questionId;

    private String question;

    private String answer;

    private boolean isTest;

    private List<String> variants = new ArrayList<>();

    public static QuestionInfoResponse fromQuestion(Question question){
        return QuestionInfoResponse.builder()
                .questionId(question.getId())
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .isTest(question.isTest())
                .variants(question.getVariants().stream().map(v -> (v.getVariant())).toList())
                .build();
    }
}
