package org.deslre;

import org.deslre.modules.filesModule.entity.UploadFile;
import org.deslre.modules.filesModule.repository.UploadFileRepository;
import org.deslre.modules.loginModule.entity.User;
import org.deslre.modules.loginModule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SeparateBackendApplicationTests {

    Logger log = LoggerFactory.getLogger(SeparateBackendApplicationTests.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    private UploadFileRepository uploadFileRepository;

    @Test
    void name() {
        User user = new User();
        user.setNickName("root");
        user.setPassWord("111111");
        userRepository.save(user);
    }

    @Test
    void update() {
        User user = new User();
        user.setId(1L);
        user.setNickName("admin");
        int updated = userRepository.updateUser(user);
        System.out.println("updated = " + updated);
    }

    @Test
    void selectUser() {
        User user = userRepository.findUserByNickName("root");
        System.out.println("user = " + user);
    }

    @Test
    void a() {

        List<UploadFile> fileList = uploadFileRepository.findAll();
        System.out.println("fileList = " + fileList);
    }

}
