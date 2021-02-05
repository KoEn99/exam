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
public class GroupStudyKey implements Serializable {
    @Column(name = "groups_table_id")
    String groupId;

    @Column(name = "auth_user_id")
    String authId;
}
