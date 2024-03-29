package com.koen.exam.dao.entity;

import com.koen.exam.dao.StatusType;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "exam_of_course")
public class ExamEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(length = 1024)
    private String description;

    @Column(name = "time_watch")
    private int timeWatch;

    @Column(name = "date_start")
    private ZonedDateTime dateStart;

    @Column(name = "date_stop")
    private ZonedDateTime dateStop;

    @Column(name = "status_active", length = 40)
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @ManyToOne
    @JoinColumn(name = "courses_table_id")
    private CoursesEntity coursesEntity;

    @Column(name = "general_score")
    private float generalScore;

    @OneToMany(mappedBy = "examEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionEntity> questionEntitiesList = new ArrayList<>();
    @OneToMany(mappedBy = "examEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TryEntity> tryEntities = new ArrayList<>();

}
