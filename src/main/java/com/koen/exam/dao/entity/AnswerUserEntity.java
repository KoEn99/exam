package com.koen.exam.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "answer_user")
public class AnswerUserEntity {
    @EmbeddedId
    AnswerUserKey id;
    @ManyToOne
    @MapsId("number_try_table_id")
    @JoinColumn(name = "number_try_table_id")
    private TryEntity tryEntity;
    @ManyToOne
    @MapsId("question_of_exam_id")
    @JoinColumn(name = "question_of_exam_id")
    private QuestionEntity questionEntity;
    @Column(name = "answer_text")
    private String answerText;
    @Column(name = "correct_answer")
    private Boolean correctAnswer;
}
