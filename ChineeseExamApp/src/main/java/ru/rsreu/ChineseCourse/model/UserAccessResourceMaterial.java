package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "user_access_resource_material")
public class UserAccessResourceMaterial {

    @EmbeddedId
    private UserAccessResourceMaterialPk id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    @MapsId("resourceId")
    private ResourceMaterial resourceMaterial;


}
