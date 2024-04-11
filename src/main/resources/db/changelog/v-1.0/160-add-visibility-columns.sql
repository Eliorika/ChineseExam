alter table lessons
    add column is_visible boolean not null default true;

alter table tests
    add column is_visible boolean not null default true;
