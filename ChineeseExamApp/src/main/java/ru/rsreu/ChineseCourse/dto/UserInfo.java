package ru.rsreu.ChineseCourse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import ru.rsreu.ChineseCourse.model.User;

import java.util.Date;

@Data
@Builder
public class UserInfo {
    @Email(message = "Provide valid email")
    @NotEmpty(message = "Provide a email")
    private String email;

    @NotEmpty(message = "Provide a first name")
    private String firstName;

    @NotEmpty(message = "Provide a last name")
    private String lastName;

    private Date registrationDate;
    private Integer strike;

    public static UserInfo fromUser(User user){
        return UserInfo.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .registrationDate(user.getCreated())
                .strike(user.getStrike())
                .build();
    }
}
