package com.koen.exam.services.Impl;

import com.koen.exam.dao.CoursesServiceDao;
import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.StatusType;
import com.koen.exam.dao.entity.AnswerUserEntity;
import com.koen.exam.dao.entity.CoursesEntity;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.services.ExamService;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import com.koen.exam.web.controller.exception.AccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamServiceDao examServiceDao;
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Override
    public ExamDto createExam(ExamDto examDto) throws AccessException {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(examDto.getCoursesEntity()).get();
        ExamEntity examEntity = new ExamEntity();
        examEntity.setTitle(examDto.getTitle());
        examEntity.setDescription(examDto.getDescription());
        examEntity.setTimeWatch(examDto.getTimeWatch());
        examEntity.setStatusType(StatusType.valueOf(examDto.getStatusType()));
        examEntity.setCoursesEntity(coursesEntity);
        examEntity = examServiceDao.createExam(examEntity);
        examDto.setId(examEntity.getId());
        return examDto;
    }

    @Override
    public List<ExamDto> getExamByCourseId(String courseId) {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(courseId).get();
        return coursesEntity.
                getExamEntityList().
                stream().
                map(ExamServiceImpl::examEntityToExamDto).
                collect(Collectors.toList());
    }

    @Override
    public List<QuestionAnswerDto> getExam(Long examId) {
        ExamEntity examEntity = examServiceDao.getExamId(examId);
        return examEntity.getQuestionEntitiesList().
                stream().
                map(ExamServiceImpl::questionEntityToQuestionAnswerDto).
                collect(Collectors.toList());
    }

    @Override
    public void updateExamEntity(ExamDto examDto) {
        float score = 0;
        ExamEntity examEntityBD = examServiceDao.getExamId(examDto.getId());
        for (QuestionEntity questionEntity:examEntityBD.getQuestionEntitiesList()){
            score += questionEntity.getScore();
        }
        examEntityBD.setGeneralScore(score);
        examServiceDao.createExam(examEntityBD);
    }

    public static QuestionAnswerDto questionEntityToQuestionAnswerDto(QuestionEntity questionEntity){
        QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
        questionAnswerDto.setId(questionEntity.getId());
        questionAnswerDto.setQuestion(questionEntity.getTitle());
        questionAnswerDto.setQuestionScore(questionEntity.getScore());
        questionAnswerDto.setQuestionType(questionEntity.getQuestionType().name());
        questionAnswerDto.setAnswers(questionEntity.
                getAnswerEntities().
                stream().map(QuestionServiceImpl::answerEntityToAnswerDtoWithoutTrueAnswer).
                collect(Collectors.toList()));
        return questionAnswerDto;
    }
    public static ExamDto examEntityToExamDto(ExamEntity examEntity){
        return new ExamDto(
                examEntity.getId(),
                examEntity.getTitle(),
                examEntity.getDescription(),
                examEntity.getTimeWatch(),
                examEntity.getDateStart(),
                examEntity.getDateStop(),
                examEntity.getStatusType().name(),
                examEntity.getCoursesEntity().getId()
        );
    }
}
