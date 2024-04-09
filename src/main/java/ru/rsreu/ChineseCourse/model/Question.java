package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.enums.QuestionType;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "assessment_materials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String question;
    private String cardText;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;


    private String answer;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    private User admin;

    @CreationTimestamp
    private Date addDate;

    @UpdateTimestamp
    private Date editDate;

    private boolean isTest;
    private boolean isGenerated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_variants",
    joinColumns = @JoinColumn(name = "question_id"),
    inverseJoinColumns = @JoinColumn(name = "variant_id"))
    private List<Variant> variants;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;

}
