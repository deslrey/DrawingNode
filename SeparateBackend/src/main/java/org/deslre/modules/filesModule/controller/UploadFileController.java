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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/uploadFile")
public class UploadFileController {

    @Resource
    private UploadFileService uploadFileService;

    /**
     * 相册分页
     */
    @PostMapping("/page")
    public Results<Map<String, Object>> getFiles(@RequestBody PageEntity pageEntity) {
        if (StringUtil.isEmpty(pageEntity)) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }

        return uploadFileService.getFiles(pageEntity);
    }


    /**
     * 保存，将新增和修改合成了一个方法，根据id判断是新增或修改
     */
    @PostMapping("/save")
    public Results<UploadFile> saveFile(@RequestBody UploadFile file) {
        if (StringUtil.isEmpty(file)) {
            return Results.fail(ResultCodeEnum.DATA_ERROR);
        }
        return uploadFileService.saveFile(file);
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

    /**
     * 上传图片，与上传信息分开，改为手动上传，点击确定后，先保存数据，再保存图片相关信息至数据库
     */
    @PostMapping("/upload")
    public Results<Integer> upload(@RequestParam Map<String, Object> map, MultipartFile file) {
        return uploadFileService.upload(map , file);
    }

}