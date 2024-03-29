CREATE TABLE answers_variance (
    id bigserial NOT NULL primary key,
    variance varchar not null,
    CONSTRAINT UK_AnswersVariance_Variance UNIQUE (variance)
);