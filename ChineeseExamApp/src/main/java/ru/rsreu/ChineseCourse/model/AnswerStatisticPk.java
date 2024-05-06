package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Data
@Embeddable
public class AnswerStatisticPk implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "response_date_time")
    @CreationTimestamp
    private Date responseDateTime;

}
