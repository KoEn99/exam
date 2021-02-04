package com.koen.exam.web.controller.dto;

import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.CoursesEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
@Data
public class ExamDto {
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
