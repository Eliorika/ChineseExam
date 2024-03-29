CREATE TABLE question_variances (
    question_id bigint NOT NULL,
    variance_id bigint not null,
    CONSTRAINT PK_QuestionVariances PRIMARY KEY (question_id, variance_id),
    CONSTRAINT FK_QuestionVariances_Question FOREIGN KEY (question_id) REFERENCES assessment_materials(question_id),
    CONSTRAINT FK_QuestionVariances_Variance FOREIGN KEY (variance_id) REFERENCES answers_variance(id)
);