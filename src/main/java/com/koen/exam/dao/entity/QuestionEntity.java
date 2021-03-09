package com.koen.exam.dao.entity;

import com.koen.exam.dao.QuestionType;
import com.koen.exam.dao.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question_of_exam")
public class QuestionEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1024, nullable = false)
    private String title;
    @Column(name = "order_question")
    private int orderQuestion;
    @Column(nullable = false)
    private float score;
    @Column(name = "question_type", length = 40, nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "exam_of_course_id")
    private ExamEntity examEntity;
    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerEntity> answerEntities = new ArrayList<>();
    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerUserEntity> answerUserEntities = new ArrayList<>();
}
