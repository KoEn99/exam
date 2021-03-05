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
    @MapsId("auth_user_id")
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;
}
