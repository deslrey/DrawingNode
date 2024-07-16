package org.deslre;

import org.deslre.common.utils.MD5;
import org.deslre.modules.loginmodule.entity.User;
import org.deslre.modules.loginmodule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SeparateBackendApplicationTests {

    @Resource
    private UserRepository userRepository;

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
        String encrypt = MD5.encrypt("111111");
        System.out.println("encrypt = " + encrypt);
    }
}
