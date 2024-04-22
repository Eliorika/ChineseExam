CREATE TABLE assessment_materials (
    id BIGSERIAL PRIMARY KEY,
    test_id bigint not null,
    question varchar NOT NULL,
    answer varchar NOT NULL,
    admin_id bigint NOT NULL,
    add_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_test boolean default false not null,
    question_type varchar(30) not null,
    card_text varchar(100),
    is_generated boolean not null default false,
    topic_id bigint,
    CONSTRAINT FK_AssessmentMaterialLesson FOREIGN KEY (test_id) REFERENCES tests(id),
    CONSTRAINT FK_AssessmentMaterialAdminLogin FOREIGN KEY (admin_id) REFERENCES users(id)
);
