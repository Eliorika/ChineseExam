package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.rsreu.ChineseCourse.model.enums.CourseDifficulty;

import java.util.Date;
import java.util.List;

@Table(name = "courses")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    @ManyToMany
    @JoinTable(
            name = "course_user",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> students;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created;


    @UpdateTimestamp
    private Date updated;

    private String description;

    private Boolean isVisible = false;
    @Enumerated(EnumType.STRING)
    private CourseDifficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User admin;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Lesson> lessons;

    @Override
    public String toString(){
        return "Курс " + this.getCourseName()
                + "\nАдмин: " + this.admin.getUsername();
    }
}
