CREATE TABLE users
(
    id bigserial primary key,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name varchar(255) not null,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_activity TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    system_role varchar(20) not null
)
GO
alter table users
    add constraint UK_email unique (email);
