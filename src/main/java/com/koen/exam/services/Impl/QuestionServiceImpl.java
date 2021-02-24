package com.koen.exam.services.Impl;

import com.koen.exam.dao.AnswerServiceDao;
import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.QuestionServiceDao;
import com.koen.exam.dao.QuestionType;
import com.koen.exam.dao.entity.AnswerEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.services.ExamService;
import com.koen.exam.services.QuestionService;
import com.koen.exam.web.controller.dto.AnswerDto;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionServiceDao questionService;
    @Autowired
    ExamServiceDao examServiceDao;
    @Autowired
    AnswerServiceDao answerServiceDao;
    @Override
    public AnswerResponse createQuestion(QuestionAnswerDto questionAnswerDto) {
        QuestionEntity questionEntity = formingQuestion(questionAnswerDto);
        List<AnswerEntity> answerEntities = questionAnswerDto.getAnswers().stream().map(
                (AnswerDto answerDto) -> formingAnswer(answerDto, questionEntity)
        ).collect(Collectors.toList());
        answerServiceDao.saveAll(answerEntities);
        return new AnswerResponse("Вопрос успешно создан");
    }
    private static AnswerEntity formingAnswer(AnswerDto answerDto, QuestionEntity questionEntity){
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setTitle(answerDto.getAnswer());
        answerEntity.setCorrectAnswer(answerDto.getAnswerCorrect());
        answerEntity.setQuestionEntity(questionEntity);
        return answerEntity;
    }
    private QuestionEntity formingQuestion(QuestionAnswerDto questionAnswerDto){
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(questionAnswerDto.getQuestion());
        questionEntity.setScore(questionAnswerDto.getQuestionScore());
        questionEntity.setQuestionType(QuestionType.valueOf(questionAnswerDto.getQuestionType()));
        questionEntity.setExamEntity(examServiceDao.getExamId(questionAnswerDto.getExamId()));
        return questionService.save(questionEntity);
    }
}
