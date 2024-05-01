alter TABLE courses
    add column if not exists is_visible boolean not null default true;