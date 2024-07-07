package org.deslre;

import org.deslre.config.NeoDatabaseConfig;
import org.deslre.handler.SpringContextHandler;
import org.deslre.loginModule.entity.UserLoginEntity;
import org.deslre.loginModule.repository.UserLoginRepository;
import org.deslre.utils.NeoUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AppTests {

    private static final Logger log = LoggerFactory.getLogger(AppTests.class);

    @Resource
    private UserLoginRepository userLoginRepository;

    @Test
    void contextLoads() {
        UserLoginEntity login = new UserLoginEntity();
        login.setUserName("李四");
        login.setPassWord("1234");
        userLoginRepository.save(login);
    }

    @Test
    void name() {
        NeoDatabaseConfig databaseConfig = SpringContextHandler.getBean(NeoDatabaseConfig.class);
        System.out.println("databaseConfig = " + databaseConfig);
    }

    @Test
    void delete() {
    }
}
