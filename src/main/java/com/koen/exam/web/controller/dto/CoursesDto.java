package com.koen.exam.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CoursesDto {
    @NotBlank(message = "Поле title не может быть пустым")
    private String title;
    @NotBlank(message = "Поле description не может быть пустым")
    @Size(max = 1024, message = "Максимальная длина 1024 символа")
    private String description;
}
