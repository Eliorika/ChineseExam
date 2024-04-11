CREATE TABLE answers_statistics (
    user_id bigint NOT NULL,
    question_id bigint NOT NULL,
    response_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    correctness BOOLEAN NOT NULL,
    CONSTRAINT PK_AnswersStatistics PRIMARY KEY (user_id, question_id, response_date_time),
    CONSTRAINT FK_AnswersStatistics_Login FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_AnswersStatistics_QuestionNumber FOREIGN KEY (question_id) REFERENCES assessment_materials(id)
);