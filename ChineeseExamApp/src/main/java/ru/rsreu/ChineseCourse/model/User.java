package ru.rsreu.ChineseCourse.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rsreu.ChineseCourse.model.enums.SystemRole;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Table(name = "users")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created;

    @UpdateTimestamp
    private Date lastActivity;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @Enumerated(EnumType.STRING)
    private SystemRole systemRole;

    @OneToMany(mappedBy = "user")
    private List<AnswerStatistic> statistics;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getStrike(){
        int iStrike = 0;
        boolean flag = true;

        List<AnswerStatistic> answerStatisticList = this.getStatistics();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date currentDate = new Date();

        if(answerStatisticList.stream().anyMatch(a->compareDate(a.getId().getResponseDateTime(),currentDate))){
            iStrike++;
        }

        while (flag){
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date previousDate = calendar.getTime();
            if(!answerStatisticList.stream().anyMatch(a->compareDate(a.getId().getResponseDateTime(),previousDate))){
                break;
            }
            iStrike++;
        }

        return iStrike;
    }

    private boolean compareDate(Date date1, Date date2){
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (localDate1.isBefore(localDate2)) {
            return false;
        } else if (localDate1.isAfter(localDate2)) {
            return false;
        }
        return true;
    }
}
