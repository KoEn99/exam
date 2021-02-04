package com.koen.exam.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "group_study")
public class GroupStudy implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;
    @Id
    @ManyToOne
    @JoinColumn(name = "courses_table_id")
    private CoursesEntity coursesEntity;
}
