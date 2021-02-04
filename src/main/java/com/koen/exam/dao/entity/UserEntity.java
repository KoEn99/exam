package com.koen.exam.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auth_user")
@Data
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

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CoursesEntity> coursesEntityList = new ArrayList<>();
}
