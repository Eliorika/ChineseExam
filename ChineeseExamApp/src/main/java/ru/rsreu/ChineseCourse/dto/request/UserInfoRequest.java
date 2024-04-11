package ru.rsreu.ChineseCourse.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequest {

    @Email(message = "Provide valid email")
    @NotEmpty(message = "Provide a email")
    private String email;

    @NotEmpty(message = "Provide a password")
    private String password;

    @NotEmpty(message = "Provide a first name")
    private String firstName;

    @NotEmpty(message = "Provide a last name")
    private String lastName;
}
