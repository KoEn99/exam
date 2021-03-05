package com.koen.exam.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerUserKey implements Serializable {
    @Column(name = "number_try_table_id")
    Long tryId;

    @Column(name = "auth_user_id")
    String authId;
}
