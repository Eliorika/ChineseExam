package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answers_statistics")
public class AnswerStatistic {

    @EmbeddedId
    private AnswerStatisticPk id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private Question question;

    private boolean correctness;

    @Transient
    private String answer;

    public AnswerStatistic(AnswerStatisticPk id, User user, Question question, boolean correctness, String answer) {
        this.id = id;
        this.user = user;
        this.question = question;
        this.correctness = correctness;
        this.answer = answer;
    }

    public AnswerStatistic() {

    }
}

