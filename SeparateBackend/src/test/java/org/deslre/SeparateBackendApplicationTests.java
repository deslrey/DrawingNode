package org.deslre;

import org.deslre.modules.loginModule.entity.User;
import org.deslre.modules.loginModule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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
        List<User> userList = userRepository.findAll();
        System.out.println("userList = " + userList);
    }

}
