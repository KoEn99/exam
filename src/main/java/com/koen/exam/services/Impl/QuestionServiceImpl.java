package com.koen.exam.services.Impl;

import com.koen.exam.dao.AnswerServiceDao;
import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.QuestionServiceDao;
import com.koen.exam.dao.QuestionType;
import com.koen.exam.dao.entity.AnswerEntity;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.QuestionEntity;
import com.koen.exam.services.ExamService;
import com.koen.exam.services.QuestionService;
import com.koen.exam.web.controller.dto.AnswerDto;
import com.koen.exam.web.controller.dto.AnswerResponse;
import com.koen.exam.web.controller.dto.ExamDto;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionServiceDao questionService;
    @Autowired
    ExamServiceDao examServiceDao;
    @Autowired
    AnswerServiceDao answerServiceDao;
    @Autowired
    ExamService examService;
    @Override
    public AnswerResponse createQuestion(QuestionAnswerDto questionAnswerDto) {
        QuestionEntity questionEntity = formingQuestion(questionAnswerDto);
        List<AnswerEntity> answerEntities = questionAnswerDto.getAnswers().stream().map(
                (AnswerDto answerDto) -> formingAnswer(answerDto, questionEntity)
        ).collect(Collectors.toList());
        answerServiceDao.saveAll(answerEntities);
        ExamDto examDto = new ExamDto();
        examDto.setId(questionEntity.getExamEntity().getId());
        examService.updateExamEntity(examDto);
        return new AnswerResponse("Вопрос успешно создан");
    }

    @Override
    public List<QuestionAnswerDto> getQuestionListByExam(Long examId) {
        ExamEntity examEntity = examServiceDao.getExamId(examId);
        List<Float> loginHard = new ArrayList<>();
        List<Float> loginHardSqr = new ArrayList<>();
        for (int i = 0; i < examEntity.getQuestionEntitiesList().size(); i++){
            int generalSize = examEntity.getQuestionEntitiesList().get(i).getAnswerUserEntities().size();
            Long answerTrueCount =
                    examEntity.getQuestionEntitiesList().
                            get(i).getAnswerUserEntities().
                            stream().filter
                            (answerUserEntity -> answerUserEntity.getCorrectAnswer().equals(true)).count();
            if (answerTrueCount == 0) continue;
            if (answerTrueCount == generalSize) continue;
            float partTrueValue = (float)answerTrueCount/(float) generalSize;
            float partFalseValue = 1 - partTrueValue;
            float TrueDivisFalse = partFalseValue/partTrueValue;
            float lnPart = (float) Math.log(TrueDivisFalse);
            float sqrLnPart = (float) Math.pow(lnPart, 2);
            loginHard.add(lnPart);
            loginHardSqr.add(sqrLnPart);
        }
        float averageHard = averageLog(loginHard, examEntity.getQuestionEntitiesList().size());
        float averageHardSqr = (float) Math.pow(averageHard, 2);
        float sumLogHardSqr = (float) loginHardSqr.stream().mapToDouble((s)->(double) s).sum();
        float dispersion = (4 - examEntity.getQuestionEntitiesList().size() * averageHardSqr)/(examEntity.getQuestionEntitiesList().size() - 1);
        //float dispersion = (sumLogHardSqr - examEntity.getQuestionEntitiesList().size() * averageHardSqr)/(examEntity.getQuestionEntitiesList().size() - 1);
        return examEntity.
                getQuestionEntitiesList().
                stream().
                map(QuestionServiceImpl::questionEntityToQuestionDto).
                collect(Collectors.toList());
    }

    private float averageLog(List<Float> logHard, int generalValueQuestion){
        float sum = 0;
        for (float x : logHard){
            sum += x;
        }
        return sum/generalValueQuestion;
    }

    @Override
    public QuestionAnswerDto getPageQuestion(Long questionId) {
        QuestionEntity questionEntity = questionService.getQuestion(questionId);
        return new QuestionAnswerDto(
                questionEntity.getId(),
                questionEntity.getTitle(),
                questionEntity.getQuestionType().name(),
                questionEntity.getExamEntity().getId(),
                questionEntity.getScore(),
                questionEntity.
                        getAnswerEntities().
                        stream().
                        map(QuestionServiceImpl::answerEntityToAnswerDto).
                        collect(Collectors.toList())
        );
    }
    public static AnswerDto answerEntityToAnswerDto(AnswerEntity answerEntity){
        return new AnswerDto(
                answerEntity.getId(),
                answerEntity.getTitle(),
                answerEntity.getCorrectAnswer()
        );
    }
    private static QuestionAnswerDto questionEntityToQuestionDto(QuestionEntity questionEntity){
        return new QuestionAnswerDto(
                questionEntity.getId(),
                questionEntity.getTitle(),
                questionEntity.getQuestionType().name(),
                questionEntity.getExamEntity().getId(),
                questionEntity.getScore(),
                null
        );
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
