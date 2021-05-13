package com.koen.exam.services.Impl;

import com.koen.exam.dao.ExamServiceDao;
import com.koen.exam.dao.GroupStudyDao;
import com.koen.exam.dao.TryServiceDao;
import com.koen.exam.dao.entity.ExamEntity;
import com.koen.exam.dao.entity.GroupEntity;
import com.koen.exam.dao.entity.TryEntity;
import com.koen.exam.services.StatService;
import com.koen.exam.web.controller.dto.StatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    TryServiceDao tryServiceDao;
    @Autowired
    GroupStudyDao groupStudyDao;
    @Autowired
    ExamServiceDao examServiceDao;
    @Override
    public List<StatDto> getStatList(String groupId, Long idExam) {
        GroupEntity groupEntity = groupStudyDao.getGroupById(groupId);
        ExamEntity examEntity = examServiceDao.getExamId(idExam);
        List<StatDto> statDtos = new LinkedList<>();
        for (int i = 0; i < groupEntity.getGroupUsers().size(); i++){
            List<TryEntity> tryEntity = tryServiceDao.
                    getTryByUserAndExamEntity(groupEntity.getGroupUsers().get(i).getUserEntity(), examEntity);
            for (int j = 0; j < tryEntity.size(); j++){
                String fio = tryEntity.get(j).getUserEntity().getLastName() + " " +
                        tryEntity.get(j).getUserEntity().getFirstName() + " " +
                        tryEntity.get(j).getUserEntity().getMiddleName();
                statDtos.add(new StatDto(fio,
                        tryEntity.size(),
                        tryEntity.get(j).getGeneralScore(),
                        0,
                        examEntity.getGeneralScore()));
            }
        }
        return statDtos;
    }
}
