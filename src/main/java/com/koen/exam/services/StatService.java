package com.koen.exam.services;

import com.koen.exam.web.controller.dto.StatDto;

import java.util.List;

public interface StatService {
    List<StatDto> getStatList(String groupId, Long idExam);
}
