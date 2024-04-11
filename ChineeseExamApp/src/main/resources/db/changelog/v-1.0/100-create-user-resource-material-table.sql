CREATE TABLE user_access_resource_material (
    user_id bigint NOT NULL,
    resource_id bigint NOT NULL,
    get_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT PK_UserAccessResourceMaterial PRIMARY KEY (user_id, resource_id, get_date),
    CONSTRAINT FK_UserAccessResourceMaterial_User FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_UserAccessResourceMaterial_QuestionNumber FOREIGN KEY (resource_id) REFERENCES resource_materials(id)
);