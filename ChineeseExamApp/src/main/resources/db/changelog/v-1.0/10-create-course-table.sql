CREATE TABLE courses (
    id bigserial primary key,
    course_name varchar(50) not null,
    admin_id bigint not null,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    description VARCHAR not null,
    difficulty VARCHAR(12) CHECK (Difficulty IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')) not null,

)
GO
alter table courses
    add constraint UK_course_name unique (course_name);

alter table courses
    add constraint FK_AdminLogin FOREIGN KEY (admin_id) REFERENCES users(id)