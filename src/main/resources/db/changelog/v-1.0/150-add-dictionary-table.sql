create table dictionary(
    variant_id int not null,
    variant_translate bigint not null,
    CONSTRAINT PK_Dictionary PRIMARY KEY (variant_id, variant_translate),
    CONSTRAINT FK_Dictionary_Variance FOREIGN KEY (variant_id) REFERENCES answers_variant(id),
    CONSTRAINT FK_Dictionary_Variance_Translate FOREIGN KEY (variant_id) REFERENCES answers_variant(id)
)