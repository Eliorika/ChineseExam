CREATE TABLE assessment_materials (
    question_id SERIAL PRIMARY KEY,
    lesson_id bigint NOT NULL,
    question varchar NOT NULL,
    answer varchar NOT NULL,
    admin_id bigint NOT NULL,
    add_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT FK_AssessmentMaterialLesson FOREIGN KEY (lesson_id) REFERENCES lessons(id),
    CONSTRAINT FK_AssessmentMaterialAdminLogin FOREIGN KEY (admin_id) REFERENCES users(id)
);
