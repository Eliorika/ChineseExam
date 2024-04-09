CREATE TABLE resource_materials (
    id bigserial not null primary key,
    material_name varchar(255) NOT NULL,
    material_text varchar NOT NULL,
    admin_id bigint NOT NULL,
    add_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT FK_ResourceMaterials_AdminLogin FOREIGN KEY (admin_id) REFERENCES users(id)
);