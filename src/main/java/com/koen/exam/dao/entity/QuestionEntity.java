package com.koen.exam.dao.entity;

import com.koen.exam.dao.QuestionType;
import com.koen.exam.dao.StatusType;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "question_of_exam")
public class QuestionEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1024, nullable = false)
    private String title;
    @Column(nullable = false)
    private int order;
    @Column(name = "question_type", length = 40, nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "exam_of_course_id")
    private ExamEntity examEntity;
    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerEntity> answerEntities = new ArrayList<>();
}
