package com.koen.exam.dao.repo;

import com.koen.exam.dao.entity.TryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TryEntityRepository extends JpaRepository<TryEntity, Long> {

}
