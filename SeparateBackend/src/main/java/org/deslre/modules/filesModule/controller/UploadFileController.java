package org.deslre.modules.filesModule.controller;

import org.deslre.common.result.ResultCodeEnum;
import org.deslre.common.result.Results;
import org.deslre.common.utils.StringUtil;
import org.deslre.modules.filesModule.entity.PageEntity;
import org.deslre.modules.filesModule.entity.UploadFile;
import org.deslre.modules.filesModule.service.UploadFileService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/uploadFile")
public class UploadFileController {

    @Resource
    private UploadFileService uploadFileService;

    /**
     * 相册分页
     */
    @GetMapping("/page")
    public Results<Page<UploadFile>> getAllFiles(@RequestParam(value = "fileName", required = false) String fileName,
                                                 @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            // 这里假设你已经实现了分页查询的方法
            return uploadFileService.getFiles(fileName, currentPage, pageSize);
        } catch (Exception e) {
            return Results.fail();
        }
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    public Results<Void> deleteFile(@RequestBody Map<String, Long> request) {
        Integer id = Math.toIntExact(request.get("id"));
        uploadFileService.deleteFile(id);
        return Results.ok();
    }

    private static final String pathFile = "E:\\list\\";

    /**
     * 上传图片，与上传信息分开，改为手动上传，点击确定后，先保存数据，再保存图片相关信息至数据库
     */
    @PostMapping("/upload")
    public Results<String> uploadFile(MultipartFile file, String fileName) {
        if (file == null || file.isEmpty()) {
            Results.fail("文件为空");
        }
        return uploadFileService.uploadFile(file, fileName);
    }
}
