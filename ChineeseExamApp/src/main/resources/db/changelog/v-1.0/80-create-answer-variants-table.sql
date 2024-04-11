CREATE TABLE answers_variant (
    id bigserial NOT NULL primary key,
    variant varchar not null,
    CONSTRAINT UK_AnswersVariance_Variance UNIQUE (variant)
);