package com.koen.exam.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses_table")
@Data
public class CoursesEntity {
    @Id
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "coursesEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExamEntity> examEntityList = new ArrayList<>();
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;
}
