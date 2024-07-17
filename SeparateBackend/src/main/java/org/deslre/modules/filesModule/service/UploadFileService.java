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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UploadFileService {

    @Resource
    private UploadFileRepository uploadFileRepository;

    public Results<Map<String, Object>> getFiles(PageEntity pageEntity) {
        String searchKey = pageEntity.getSearchKey();
        int pageNum = pageEntity.getPageNum();
        int pageSize = pageEntity.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        Page<UploadFile> page = uploadFileRepository.findByFileNameContaining(searchKey, pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("list", page.getContent());
        response.put("total", page.getTotalElements());
        response.put("pageNum", page.getNumber() + 1);
        response.put("pageSize", page.getSize());
        return Results.ok(response);
    }

    public Results<UploadFile> saveFile(UploadFile file) {
        if (StringUtil.isEmpty(file)) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        if (file.getId() != null) {
            uploadFileRepository.save(file);
        } else {
            uploadFileRepository.save(file);
        }
        return Results.ok(file);
    }

    public void deleteFile(Integer id) {
        Optional<UploadFile> optionalFile = uploadFileRepository.findById(id);
        if (optionalFile.isPresent()) {
            UploadFile file = optionalFile.get();
            uploadFileRepository.save(file);
        }
    }

    private static final String path = "E:\\list";

    public Results<Integer> upload(Map<String, Object> map, MultipartFile file) {
        try {
//            Integer id = map.get("id") != null ? Integer.valueOf((String) map.get("id")) : null;
            Integer id = null;
            Object idObj = map.get("id");
            if (idObj instanceof String) {
                id = Integer.valueOf((String) idObj);
            } else if (idObj instanceof Integer) {
                id = (Integer) idObj;
            }
            if (id != null) {
                // 根据 noid 查询数据库中是否已存在记录
                UploadFile existingFile = uploadFileRepository.findById(id).orElse(null);

                if (existingFile != null) {
                    // 删除旧文件
                    if (existingFile.getRelativePath() != null) {
                        FileUtils.deleteFile(path + File.separator + existingFile.getRelativePath());
                    }
                    // 更新文件信息
                    existingFile.setFileName((String) map.get("fileName"));
                    String relativePath = FileUtils.upload(file, path);
                    existingFile.setRelativePath(relativePath);

                    uploadFileRepository.save(existingFile);

                    return Results.ok(id); // 返回成功结果，包含 id
                }
            }

            // 创建新文件记录
            UploadFile newFile = new UploadFile();
            newFile.setFileName((String) map.get("fileName"));
            String relativePath = FileUtils.upload(file, path);
            newFile.setRelativePath(relativePath);

            UploadFile savedFile = uploadFileRepository.save(newFile);

            return Results.ok(savedFile.getId()); // 返回成功结果，包含保存后的主键 ID
        } catch (IOException e) {
            e.printStackTrace();
            return Results.fail("文件上传失败：" + e.getMessage());
        }
    }
}

