package com.koen.exam.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesEntity {
    @Id
    private String id;
    @Column
    private String title;
    @Column
    private String description;
    @OneToMany(mappedBy = "coursesEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExamEntity> examEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "coursesEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupEntity> groupEntities = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;
}
