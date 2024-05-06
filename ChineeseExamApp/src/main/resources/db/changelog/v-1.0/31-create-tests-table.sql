CREATE TABLE tests (
                         id bigserial PRIMARY KEY,
                         lesson_id BIGINT NOT NULL,
                         admin_id bigint NOT NULL,
                         add_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE tests
    ADD CONSTRAINT FK_LessonCourse FOREIGN KEY (lesson_id) REFERENCES lessons(id),
    ADD CONSTRAINT FK_LessonAdminLogin FOREIGN KEY (admin_id) REFERENCES users(id);
    --ADD CONSTRAINT UK_Lesson UNIQUE (lesson_name, course_id);


