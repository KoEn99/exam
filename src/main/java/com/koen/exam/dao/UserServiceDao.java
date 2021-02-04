package com.koen.exam.dao;

import com.koen.exam.dao.entity.RoleEntity;
import com.koen.exam.dao.entity.UserEntity;
import com.koen.exam.dao.repo.RoleEntityRepository;
import com.koen.exam.dao.repo.UserEntityRepository;
import com.koen.exam.web.controller.dto.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceDao {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private RoleEntityRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity saveUser(AuthDto authDto) {
        UserEntity userEntity = new UserEntity();
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_" + authDto.getRole());
        userEntity.setRoleEntity(userRole);
        userEntity.setLogin(authDto.getLogin());
        userEntity.setPassword(authDto.getPassword());
        userEntity.setFirstName(authDto.getFirstName());
        userEntity.setLastName(authDto.getLastName());
        userEntity.setMiddleName(authDto.getMiddleName());
        String authId = UUID.randomUUID().toString();
        userEntity.setId(authId);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) {

        return null;
    }
}
