package org.deslre.modules.filesModule.repository;

import org.deslre.modules.filesModule.entity.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UploadFileRepository extends JpaRepository<UploadFile, Integer> {

    @Query("SELECT u FROM UploadFile u WHERE u.exits = true and u.fileName LIKE %:fileName%")
    Page<UploadFile> findByFileNameContainingAndExitsIsTrue(@Param("fileName") String fileName, Pageable pageable);

    Optional<List<UploadFile>> findAllByExitsIsTrue();

    @Modifying
    @Transactional
    @Query("UPDATE UploadFile u SET u.exits = false WHERE u.id = ?1")
    void softDeleteById(Integer id);

    Optional<UploadFile> findByIdAndExitsIsTrue(Integer id);


}