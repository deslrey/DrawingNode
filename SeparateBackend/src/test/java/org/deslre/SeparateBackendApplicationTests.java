package org.deslre;

import org.deslre.common.utils.StringUtil;
import org.deslre.modules.filesModule.entity.UploadFile;
import org.deslre.modules.filesModule.repository.UploadFileRepository;
import org.deslre.modules.loginModule.entity.User;
import org.deslre.modules.loginModule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Optional;

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

//        UploadFile uploadFile = uploadFileRepository.findByIdAndExitsIsTrue(49).get();
//        System.out.println("uploadFile = " + uploadFile);
    }

}
