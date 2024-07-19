package org.deslre.modules.filesModule.repository;

import org.deslre.modules.filesModule.entity.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UploadFileRepository extends JpaRepository<UploadFile, Integer> {

    @Query("SELECT u FROM UploadFile u WHERE u.fileName LIKE %:fileName%")
    Page<UploadFile> findByFileNameContaining(@Param("fileName") String fileName, Pageable pageable);

}