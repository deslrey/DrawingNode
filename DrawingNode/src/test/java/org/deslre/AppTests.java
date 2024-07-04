package org.deslre;

import org.deslre.loginModule.entity.UserLogin;
import org.deslre.loginModule.repository.UserLoginRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class AppTests {

    @Resource
    private UserLoginRepository userLoginRepository;

    @Test
    void contextLoads() {
        UserLogin login = new UserLogin();
        login.setUserName("李四");
        login.setPassWord("1234");
        userLoginRepository.save(login);
    }

}
