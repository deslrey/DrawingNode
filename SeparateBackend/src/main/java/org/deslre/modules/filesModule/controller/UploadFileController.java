package org.deslre.modules.filesModule.controller;

import org.deslre.common.result.Results;
import org.deslre.modules.filesModule.entity.PageEntity;
import org.deslre.modules.filesModule.entity.UploadFile;
import org.deslre.modules.filesModule.service.UploadFileService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
        String searchKey = pageEntity.getSearchKey();
        int pageNum = pageEntity.getPageNum();
        int pageSize = pageEntity.getPageSize();

        Page<UploadFile> filePage = uploadFileService.getFiles(searchKey, pageNum, pageSize);
        Map<String, Object> response = new HashMap<>();
        response.put("list", filePage.getContent());
        response.put("total", filePage.getTotalElements());
        response.put("pageNum", filePage.getNumber() + 1);
        response.put("pageSize", filePage.getSize());

        return Results.ok(response);
    }


    /**
     * 保存，将新增和修改合成了一个方法，根据id判断是新增或修改
     */
    @PostMapping("/save")
    public Results<UploadFile> saveFile(@RequestBody UploadFile file) {
        UploadFile savedFile = uploadFileService.saveFile(file);
        return Results.ok(savedFile);
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
        return uploadFileService.upload(map, file);
    }

}
