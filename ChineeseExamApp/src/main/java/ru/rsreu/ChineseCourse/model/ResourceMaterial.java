package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource_materials")
public class ResourceMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materialName;


    private String materialText;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    private User admin;


    @CreationTimestamp
    private Date addDate;

    @UpdateTimestamp
    private Date editDate;

    @ManyToMany(mappedBy = "resourceMaterialList")
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "resourceMaterial")
    private List<UserAccessResourceMaterial> userAccessResourceMaterials;


}