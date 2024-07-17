package org.deslre.modules.filesModule.repository;

import org.deslre.modules.filesModule.entity.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Integer> {

    Page<UploadFile> findByFileNameContaining(String fileName, Pageable pageable);

}