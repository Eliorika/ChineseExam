CREATE TABLE lessons (
                         id bigserial PRIMARY KEY,
                         lesson_name varchar(255) NOT NULL,
                         course_id int NOT NULL,
                         admin_id bigint NOT NULL,
                         difficulty VARCHAR(6) NOT NULL CHECK (difficulty IN ('EASY', 'NORMAL', 'HARD')),
                         add_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         description varchar NOT NULL
);

ALTER TABLE lessons
    ADD CONSTRAINT FK_LessonCourse FOREIGN KEY (course_id) REFERENCES courses(id),
    ADD CONSTRAINT FK_LessonAdminLogin FOREIGN KEY (admin_id) REFERENCES users(id),
    ADD CONSTRAINT UK_Lesson UNIQUE (lesson_name, course_id);


