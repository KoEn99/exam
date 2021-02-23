package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {

    @NotBlank(message = "Поле login не может быть пустым")
    @Email(message = "Некорректно введен адрес электронной почты")
    private String login;
    @NotBlank(message = "Поле password не может быть пустым")
    @Size(min = 8, message = "Минимальная длина пароля 8 символов")
    private String password;
    @NotBlank(message = "Поле firstName не может быть пустым")
    private String firstName;
    @NotBlank(message = "Поле lastName не может быть пустым")
    private String lastName;
    @NotBlank(message = "Поле middleName не может быть пустым")
    private String middleName;
    @NotBlank(message = "Поле role не может быть пустым")
    private String role;
}
