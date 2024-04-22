package ru.rsreu.ChineseCourse.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @OneToMany(mappedBy = "test")
    private List<Question> questions;

    private Boolean isVisible;

    @ManyToOne
    @JoinColumn(name = "admin_id",
            referencedColumnName = "id", nullable = false)
    private User admin;

    @ManyToMany
    @JoinTable(name = "lesson_reference_materials",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<ResourceMaterial> resourceMaterialList;

}
