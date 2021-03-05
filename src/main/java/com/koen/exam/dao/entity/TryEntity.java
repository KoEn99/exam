package com.koen.exam.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "number_try_table")
@Data
public class TryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "exam_of_course_id")
    private ExamEntity examEntity;

    @OneToMany(mappedBy = "tryEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerUserEntity> answerUserEntities = new ArrayList<>();
}
