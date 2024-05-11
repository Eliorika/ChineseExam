package ru.rsreu.ChineseCourse.dto.response;

import lombok.*;
import ru.rsreu.ChineseCourse.model.Question;

import java.util.ArrayList;
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
