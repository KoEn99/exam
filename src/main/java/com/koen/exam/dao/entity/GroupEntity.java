package com.koen.exam.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups_table")
@Data
@NoArgsConstructor
public class GroupEntity {
    @Id
    private String id;
    @Column
    private String name;
    @Column(name = "group_name_search")
    private String groupNameSearch;
    @ManyToOne
    @JoinColumn(name = "courses_table_id")
    private CoursesEntity coursesEntity;

    @OneToMany(mappedBy = "groupEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupUser> groupUsers = new ArrayList<>();

    public GroupEntity(String name, CoursesEntity coursesEntity) {
        this.name = name;
        this.coursesEntity = coursesEntity;
    }
}
