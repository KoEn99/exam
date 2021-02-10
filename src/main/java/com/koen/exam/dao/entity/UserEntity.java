package com.koen.exam.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auth_user")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;
    @Column
    private String login;
    @Column
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middleName")
    private String middleName;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CoursesEntity> coursesEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupUser> groupStudies = new ArrayList<>();
    public UserEntity (String firstName, String lastName, String middleName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }
}
