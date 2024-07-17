package org.deslre.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtils {

    public static String upload(MultipartFile file, String uploadPath) throws IOException {

        String finalFileName = "";
        if (file.getSize() > 0) {
            String originalFilename = file.getOriginalFilename();
            //获取源文件的后缀名
            String extName = null;
            if (originalFilename != null) {
                extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            //获取年月
            SimpleDateFormat sdfYyyyMMdd = new SimpleDateFormat("yyyy-MM");
            finalFileName = sdfYyyyMMdd.format(new Date()) + File.separator;

            //获取日
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            finalFileName += sdf.format(new Date()) + File.separator;

            //获取时分秒
            SimpleDateFormat sdfHHmmss = new SimpleDateFormat("HHmmss");
            finalFileName += sdfHHmmss.format(new Date());

            //生成4位随机数字
            Integer rndNum = new Random().nextInt(1000) + 9000;

            //拼接随机数字和后缀名
            finalFileName += rndNum + extName;

            //目标文件
            File f1 = new File(uploadPath + File.separator + finalFileName);
            if (!f1.exists()) {
                f1.mkdirs();
            }
            //开始上传
            file.transferTo(f1);
        }
        return finalFileName;
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

}
