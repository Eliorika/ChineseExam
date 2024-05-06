package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Table(name="answers_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String variant;

    @ManyToMany(mappedBy = "variants")
    private List<Topic> topics;

    public Variant(String variant){
        this.variant = variant;
    }

}
