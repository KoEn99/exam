package com.koen.exam.web.controller.exception;

import com.koen.exam.dao.*;
import com.koen.exam.dao.entity.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("access")
public class GlobalAccess {
    @Autowired
    CoursesServiceDao coursesServiceDao;
    @Autowired
    UserServiceDao userServiceDao;
    @Autowired
    GroupStudyDao groupStudyDao;
    @Autowired
    ExamServiceDao examServiceDao;
    @Autowired
    QuestionServiceDao questionServiceDao;

    public boolean accessCourse(
            @NonNull final UserDetails userDetails,
            @NonNull final String courseId
    ) throws AccessException {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(courseId).get();
        if (!userDetails.getUsername().equals(coursesEntity.getUserEntity().getLogin())) {
            throw new AccessException();
        }
        return true;
    }

    public boolean accessJoinGroup(@NonNull final UserDetails userDetails,
                                   @NonNull final String nameGroup) throws GroupException {
        UserEntity userEntity = userServiceDao.findByLogin(userDetails.getUsername());
        GroupEntity groupEntity = groupStudyDao.getGroupEntity(nameGroup);
        boolean isJoinInCourse = userEntity.getGroupStudies().stream().anyMatch(groupUser ->
                groupUser.getGroupEntity().getCoursesEntity().getId().equals(groupEntity.getCoursesEntity().getId()));
        if (isJoinInCourse){
            throw new GroupException("Вы уже являетесь участником данного курса");
        }
        return true;
    }
    public boolean accessUserByGroup(@NonNull final UserDetails userDetails,
                                   @NonNull final String nameGroup) throws AccessException {
        GroupEntity groupEntity = groupStudyDao.getGroupEntity(nameGroup);
        if (!groupEntity.getCoursesEntity().getUserEntity().getLogin().equals(userDetails.getUsername())){
            throw new AccessException();
        }
        return true;
    }
    public boolean accessPageCourse(@NonNull final UserDetails userDetails,
                                    @NonNull final String courseId) throws AccessException {
        CoursesEntity coursesEntity = coursesServiceDao.getCourseEntity(courseId).get();
        UserEntity userEntity = userServiceDao.findByLogin(userDetails.getUsername());
        boolean groupEntity = userEntity.getGroupStudies().stream().anyMatch(groupUser ->
                    groupUser.getGroupEntity().getCoursesEntity().getId().equals(coursesEntity.getId()));
        if (!groupEntity) throw new AccessException();
        return true;
    }
    public boolean accessSubmit(@NonNull final UserDetails userDetails,
                                    @NonNull final Long questionId) throws AccessException {
        QuestionEntity questionEntity = questionServiceDao.getQuestion(questionId);
        UserEntity userEntity = userServiceDao.findByLogin(userDetails.getUsername());
        boolean groupEntity = userEntity.getGroupStudies().stream().anyMatch(groupUser ->
                groupUser.getGroupEntity().getCoursesEntity().getId().equals(
                        questionEntity.getExamEntity().getCoursesEntity().getId()
                ));
        if (!groupEntity) throw new AccessException();
        return true;
    }
    public boolean accessExam(@NonNull final UserDetails userDetails,
                                @NonNull final Long examId) throws AccessException {
        ExamEntity examEntity = examServiceDao.getExamId(examId);
        UserEntity userEntity = userServiceDao.findByLogin(userDetails.getUsername());
        boolean groupEntity = userEntity.getGroupStudies().stream().anyMatch(groupUser ->
                groupUser.getGroupEntity().getCoursesEntity().getId().equals(
                        examEntity.getCoursesEntity().getId()
                ));
        if (!groupEntity) throw new AccessException();
        return true;
    }
    public boolean accessCreateQuestion(@NonNull final UserDetails userDetails,
                                        @NonNull final Long examId) throws AccessException {
        ExamEntity examEntity = examServiceDao.getExamId(examId);
        if (!examEntity.getCoursesEntity().getUserEntity().getLogin().equals(userDetails.getUsername())){
            throw new AccessException();
        }
        return true;
    }
    public boolean accessPageQuestion(@NonNull final UserDetails userDetails,
                                      @NonNull final long questionId) throws AccessException {
        QuestionEntity questionEntity = questionServiceDao.getQuestion(questionId);
        if (!questionEntity.getExamEntity().getCoursesEntity().
                getUserEntity().getLogin().equals(userDetails.getUsername())){
            throw new AccessException();
        }
        return true;
    }
}
