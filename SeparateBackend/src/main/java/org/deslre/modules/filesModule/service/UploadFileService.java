package org.deslre.modules.filesModule.service;

import org.deslre.common.result.Results;
import org.deslre.modules.filesModule.entity.PageEntity;
import org.deslre.modules.filesModule.entity.UploadFile;
import org.deslre.modules.filesModule.repository.UploadFileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Service
public class UploadFileService {

    @Resource
    private UploadFileRepository uploadFileRepository;

    public Page<UploadFile> getFiles(String searchKey, int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return uploadFileRepository.findByFileNameContaining(searchKey, pageRequest);
    }

    public UploadFile saveFile(UploadFile file) {
        return uploadFileRepository.save(file);
    }

    public void deleteFile(Integer id) {
        Optional<UploadFile> optionalFile = uploadFileRepository.findById(id);
        if (optionalFile.isPresent()) {
            UploadFile file = optionalFile.get();
            uploadFileRepository.save(file);
        }
    }

    public Results<Integer> upload(Map<String, Object> map, MultipartFile file) {
        return null;
    }
}
