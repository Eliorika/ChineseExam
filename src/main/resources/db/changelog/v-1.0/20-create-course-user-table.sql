CREATE TABLE course_user(
    user_id bigint not null,
    course_id bigint not null,
    primary key(user_id, course_id)
);
GO

alter table course_user
    add CONSTRAINT FK_StudentLogin FOREIGN KEY (user_id) REFERENCES users(id);
alter table course_user
    add CONSTRAINT FK_CourseName FOREIGN KEY (course_id) REFERENCES courses(id);