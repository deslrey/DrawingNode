package org.deslre.modules.loginModule.controller;

import com.google.code.kaptcha.Producer;
import org.deslre.common.utils.FinalUtil;
import org.deslre.common.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/captcha")
public class VerificationCodeController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    //注入redis模板
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/captchaImage")
    public void getCaptchaImage(HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            //禁止浏览器缓存验证码图片的响应头
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");

            //响应正文为jpg图片即验证码图片
            response.setContentType("image/jpeg");

            //生成验证码文本
            String code = captchaProducer.createText();
            //生成验证码图片
            BufferedImage bi = captchaProducer.createImage(code);

            String encrypt = MD5.encrypt(code);

            //将验证码文本存储到redis
            stringRedisTemplate.opsForValue().set(FinalUtil.IMAGE_PATH, encrypt, FinalUtil.IMAGE_OUT_TIME, TimeUnit.MINUTES);

            //将验证码图片响应给浏览器
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
