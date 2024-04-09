package ru.rsreu.ChineseCourse.dto.response;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.Lesson;
import ru.rsreu.ChineseCourse.model.ResourceMaterial;
import ru.rsreu.ChineseCourse.model.User;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ResourceMaterialInfoResponse {
    private Long id;

    private String materialName;

    private String materialText;


    public static ResourceMaterialInfoResponse fromResourceMaterial(ResourceMaterial material){
        return ResourceMaterialInfoResponse.builder()
                .id(material.getId())
                .materialName(material.getMaterialName())
                .materialText(material.getMaterialText())
                .build();
    }
}