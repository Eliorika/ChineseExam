CREATE TABLE question_variants (
    question_id bigint NOT NULL,
    variant_id bigint not null,
    CONSTRAINT PK_QuestionVariances PRIMARY KEY (question_id, variant_id),
    CONSTRAINT FK_QuestionVariances_Question FOREIGN KEY (question_id) REFERENCES assessment_materials(id),
    CONSTRAINT FK_QuestionVariances_Variance FOREIGN KEY (variant_id) REFERENCES answers_variant(id)
);