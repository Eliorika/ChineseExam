create table topics(
    id bigserial primary key,
    name varchar not null,
    CONSTRAINT UK_Topics_Name UNIQUE (name)
)