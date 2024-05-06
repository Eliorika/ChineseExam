create table topics_variants(
    topic_id bigint not null,
    variant_id bigint not null,
    CONSTRAINT PK_TopicsVariants PRIMARY KEY (topic_id, variant_id),
    CONSTRAINT FK_TopicsVariants_Topic FOREIGN KEY (topic_id) REFERENCES topics(id),
    CONSTRAINT FK_TopicsVariants_Variance FOREIGN KEY (variant_id) REFERENCES answers_variant(id)
)