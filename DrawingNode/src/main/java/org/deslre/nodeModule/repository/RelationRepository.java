package org.deslre.nodeModule.repository;

import org.deslre.nodeModule.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRepository extends JpaRepository<RelationEntity, Integer> {

    List<RelationEntity> findAllByCaseNumber(String caseNumber);

}
