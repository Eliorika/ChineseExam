CREATE TABLE lesson_reference_materials (
    lesson_id bigint NOT NULL,
    material_id bigint NOT NULL,
    CONSTRAINT PK_LessRefMaterial PRIMARY KEY (lesson_id, material_id),
    CONSTRAINT FK_LessRefMaterial_LessName FOREIGN KEY (lesson_id) REFERENCES lessons(id),
    CONSTRAINT FK_LessRefMaterial_MatName FOREIGN KEY (material_id) REFERENCES resource_materials(id)
);