package com.koen.exam.web.controller.dto;

import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.CoursesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Long id;
    @NotBlank(message = "Поле title не может быть пустым")
    private String title;
    @NotBlank(message = "Поле description не может быть пустым")
    private String description;

    private int timeWatch;

    private ZonedDateTime dateStart;

    private ZonedDateTime dateStop;

    @NotBlank(message = "Поле statusType не может быть пустым")
    private String statusType;
    @NotBlank(message = "Поле coursesEntity не может быть пустым")
    private String coursesEntity;
}
