package com.koen.exam.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "group_user")
@NoArgsConstructor
public class GroupUser implements Serializable {
    @EmbeddedId
    GroupStudyKey id;
    @ManyToOne
    @MapsId("auth_user_id")
    @JoinColumn(name = "auth_user_id")
    private UserEntity userEntity;
    @ManyToOne
    @MapsId("groups_table_id")
    @JoinColumn(name = "groups_table_id")
    private GroupEntity groupEntity;
}
