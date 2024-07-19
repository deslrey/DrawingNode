package org.deslre.modules.filesModule.service;

import org.deslre.common.result.ResultCodeEnum;
import org.deslre.common.result.Results;
import org.deslre.common.utils.FileUtils;
import org.deslre.common.utils.StringUtil;
import org.deslre.modules.filesModule.entity.PageEntity;
import org.deslre.modules.filesModule.entity.UploadFile;
import org.deslre.modules.filesModule.repository.UploadFileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UploadFileService {

    @Resource
    private UploadFileRepository uploadFileRepository;

    public Results<Page<UploadFile>> getFiles(String fileName, int page, int size) {
        if (page == 0)
            page = 1;
        Pageable pageable = PageRequest.of(page - 1, size); // 页码从0开始
        Page<UploadFile> uploadFilePage = uploadFileRepository.findByFileNameContaining(fileName, pageable);
        return Results.ok(uploadFilePage);
    }


    public void deleteFile(Integer id) {
        Optional<UploadFile> optionalFile = uploadFileRepository.findById(id);
        if (optionalFile.isPresent()) {
            UploadFile file = optionalFile.get();
            FileUtils.deleteFile(path + File.separator + file.getRelativePath());
            uploadFileRepository.delete(file);
        }
    }

    private static final String path = "E:\\list";

    public Results<String> uploadFile(MultipartFile file, String fileName) {
        if (file == null || file.isEmpty()) {
            return Results.fail("文件为空");
        }
        fileName = Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().lastIndexOf("."));
        try {
            String uploaded = FileUtils.upload(file, path, fileName);
            UploadFile uploadFile = new UploadFile();
            uploadFile.setFileName(fileName);
            uploadFile.setRelativePath(uploaded);
            uploadFileRepository.save(uploadFile);

            return Results.ok("文件上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Results.fail("文件上传失败");
        }


    }

}

