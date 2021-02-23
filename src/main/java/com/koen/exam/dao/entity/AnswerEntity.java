package com.koen.exam.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer_of_question")
public class AnswerEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1024, nullable = false)
    private String title;
    @Column(name = "correct_answer", nullable = false)
    private Boolean correctAnswer;
    @ManyToOne
    @JoinColumn(name = "question_of_exam_id")
    private QuestionEntity questionEntity;
}
