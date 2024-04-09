CREATE TABLE assessment_reference_table(
    assessment_id bigint not null,
    resource_id bigint not null,
    primary key(assessment_id, resource_id)
);
GO

alter table assessment_reference_table
    add CONSTRAINT FK_AssessmentReference_Assessment FOREIGN KEY (assessment_id) REFERENCES assessment_materials(id);
alter table assessment_reference_table
    add CONSTRAINT FK_AssessmentReference_Resource FOREIGN KEY (resource_id) REFERENCES resource_materials(id);