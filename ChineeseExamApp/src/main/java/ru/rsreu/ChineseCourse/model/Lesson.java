package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonName;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "admin_id",
            referencedColumnName = "id", nullable = false)
    private User admin;

    private Boolean isVisible;


    @ManyToMany
    @JoinTable(name = "lesson_reference_materials",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<ResourceMaterial> resourceMaterialList;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Test> tests;


    @CreationTimestamp
    private Date addDate;

    @UpdateTimestamp
    private Date editDate;

    private String description;

    // Getters and setters, equals and hashcode methods
}