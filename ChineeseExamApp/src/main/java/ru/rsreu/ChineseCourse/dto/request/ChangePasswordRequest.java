package ru.rsreu.ChineseCourse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    //TODO valid new password equals

    @NotBlank(message = "Предоставьте пароль")
    String oldPassword;

    @NotBlank(message = "Введите новый пароль")
    String newPassword;

    @NotBlank(message = "Повторите новый пароль")
    String confirmPassword;
}
