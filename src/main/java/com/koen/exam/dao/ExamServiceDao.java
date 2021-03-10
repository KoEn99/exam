package com.koen.exam.dao;

import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.repo.CoursesEntityRepository;
import com.koen.exam.dao.repo.ExamEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceDao {
    @Autowired
    ExamEntityRepository examEntityRepository;
    @Autowired
    CoursesEntityRepository coursesEntityRepository;
    public ExamEntity createExam(ExamEntity examEntity){
        return examEntityRepository.save(examEntity);
    }
    public ExamEntity getExamId(Long examId){return examEntityRepository.findById(examId).get();}
    public List<ExamEntity> findAllByCoursesEntityAndStatusType(CoursesEntity coursesEntity, StatusType statusType){
        return examEntityRepository.findAllByCoursesEntityAndStatusType(coursesEntity, statusType);
    }

}
