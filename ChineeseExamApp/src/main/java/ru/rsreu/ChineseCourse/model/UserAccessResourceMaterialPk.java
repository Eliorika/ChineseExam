package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Data
@Embeddable
public class UserAccessResourceMaterialPk implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "get_date")
    @CreationTimestamp
    private Date getDate;
}
