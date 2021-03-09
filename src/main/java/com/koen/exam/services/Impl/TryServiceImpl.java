package com.koen.exam.services.Impl;

import com.koen.exam.dao.*;
import com.koen.exam.dao.entity.*;
import com.koen.exam.security.CustomUserDetails;
import com.koen.exam.services.TryService;
import com.koen.exam.web.controller.dto.AnswerDto;
import com.koen.exam.web.controller.dto.QuestionAnswerDto;
import com.koen.exam.web.controller.dto.TryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TryServiceImpl implements TryService {
    @Autowired
    private TryServiceDao tryServiceDao;
    @Autowired
    private QuestionServiceDao questionServiceDao;
    @Autowired
    private AnswerServiceDao answerServiceDao;
    @Autowired
    private UserServiceDao userServiceDao;
    @Autowired
    private UserAnswerServiceDao userAnswerServiceDao;

    @Override
    public TryDto trySubmit(List<QuestionAnswerDto> questionAnswerDtoList) {
        float generalScore = 0;
        float score = 0;
        QuestionEntity questionEntity1 = questionServiceDao.getQuestion(questionAnswerDtoList.get(0).getId());
        ExamEntity examEntity = questionEntity1.getExamEntity();
        List<QuestionEntity> questionEntities = examEntity.getQuestionEntitiesList();
        for (QuestionAnswerDto questionAnswerDto:questionAnswerDtoList){
            QuestionEntity questionEntity = questionServiceDao.getQuestion(questionAnswerDto.getId());
            questionEntities.remove(questionEntity);
            List<AnswerEntity> answerEntities = answerServiceDao.
                    getAnswerEntityByQuestionAndCorrectAnswer(questionEntity, true);
            switch (questionEntity.getQuestionType()){
                case FREE_ANSWER:{
                    if (answerEntities.get(0).getTitle().
                            equals(questionAnswerDto.getAnswers().get(0).getAnswer())){
                        score += questionEntity.getScore();
                        questionAnswerDto.getAnswers().get(0).setAnswerCorrect(true);
                    } else questionAnswerDto.getAnswers().get(0).setAnswerCorrect(false);
                    break;
                }
                case MULTIPLE:{
                    boolean isCorrect = false;
                    if (questionAnswerDto.getAnswers().size() == answerEntities.size()) {
                        for (int i = 0; i < questionAnswerDto.getAnswers().size(); i++) {
                            if (questionAnswerDto.getAnswers().get(i).getId().equals(
                                    answerEntities.get(i).getId()
                            )){
                                isCorrect = true;
                                questionAnswerDto.getAnswers().get(i).setAnswerCorrect(true);
                            } else {
                                isCorrect = false;
                                questionAnswerDto.getAnswers().get(i).setAnswerCorrect(false);
                            }
                        }
                    }
                    if (isCorrect) score += questionEntity.getScore();
                    break;
                }
                case SINGLE:{
                    if (answerEntities.get(0).getId().
                            equals(questionAnswerDto.getAnswers().get(0).getId())){
                        score += questionEntity.getScore();
                        questionAnswerDto.getAnswers().get(0).setAnswerCorrect(true);
                    } else questionAnswerDto.getAnswers().get(0).setAnswerCorrect(false);
                    break;
                }
            }
        }
        TryEntity tryEntity = saveTryUser(score, examEntity);
        saveAnswerUser(questionAnswerDtoList, tryEntity);
        submitUnMarkedQuestion(questionEntities, tryEntity);
        return new TryDto(score, examEntity.getGeneralScore());
    }
    private void submitUnMarkedQuestion(List<QuestionEntity> questionEntities, TryEntity tryEntity){
        List<AnswerUserEntity> answerUserEntities = new ArrayList<>();
        for (QuestionEntity questionEntity:questionEntities){
            AnswerUserEntity answerUserEntity = new AnswerUserEntity();
            AnswerUserKey answerUserKey = new AnswerUserKey(tryEntity.getId(),questionEntity.getId());
            answerUserEntity.setId(answerUserKey);
            answerUserEntity.setTryEntity(tryEntity);
            answerUserEntity.setQuestionEntity(questionEntity);
            answerUserEntity.setCorrectAnswer(false);
            answerUserEntities.add(answerUserEntity);
        }
        userAnswerServiceDao.saveAnswerUserService(answerUserEntities);
    }
    private TryEntity saveTryUser(float score, ExamEntity examEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        UserEntity userEntity = userServiceDao.findByLogin(login);
        TryEntity tryEntity = new TryEntity();
        tryEntity.setUserEntity(userEntity);
        tryEntity.setExamEntity(examEntity);
        tryEntity.setGeneralScore(score);
        return tryServiceDao.saveTry(tryEntity);
    }
    private void saveAnswerUser(List<QuestionAnswerDto> questionAnswerDtoList, TryEntity tryEntity){
        List<AnswerUserEntity> answerUserEntities = new ArrayList<>();
        for (QuestionAnswerDto questionAnswerDto:questionAnswerDtoList){
            String answerText = "";
            QuestionEntity questionEntity = questionServiceDao.getQuestion(questionAnswerDto.getId());
            AnswerUserEntity answerUserEntity = new AnswerUserEntity();
            AnswerUserKey answerUserKey = new AnswerUserKey(tryEntity.getId(),questionAnswerDto.getId());
            answerUserEntity.setId(answerUserKey);
            answerUserEntity.setTryEntity(tryEntity);
            answerUserEntity.setQuestionEntity(questionEntity);
            for (AnswerDto answerDto:questionAnswerDto.getAnswers()){
                if (answerDto.getId() == null){
                    answerText += answerDto.getAnswer() + "; " ;
                    answerUserEntity.setAnswerText(answerText);
                } else {
                    AnswerEntity answerEntity = answerServiceDao.getAnswerEntityById(answerDto.getId());
                    answerText += answerEntity.getTitle() + "; " ;
                    answerUserEntity.setAnswerText(answerText);
                }
                answerUserEntity.setCorrectAnswer(answerDto.getAnswerCorrect());
            }
            answerUserEntities.add(answerUserEntity);
        }
        userAnswerServiceDao.saveAnswerUserService(answerUserEntities);
    }
}
