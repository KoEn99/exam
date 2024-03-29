package com.koen.exam.web.controller.dto;

import com.koen.exam.dao.entity.CoursesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDto {
    private String id;
    private String name;
    private CoursesEntity coursesEntity;
}
