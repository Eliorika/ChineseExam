package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="topics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @ManyToMany
    @JoinTable(
            name = "topics_variants",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "variant_id"))
    private List<Variant> variants;
}
