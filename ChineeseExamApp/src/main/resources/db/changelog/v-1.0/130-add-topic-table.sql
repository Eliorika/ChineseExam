create table topics(
    id bigserial primary key,
    name varchar(100) not null,
    CONSTRAINT UK_Topics_Name UNIQUE (name)
)