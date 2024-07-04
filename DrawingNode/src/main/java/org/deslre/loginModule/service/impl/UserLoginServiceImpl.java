package org.deslre.loginModule.service.impl;

import org.deslre.loginModule.entity.UserLogin;
import org.deslre.loginModule.repository.UserLoginRepository;
import org.deslre.loginModule.service.UserLoginService;
import org.deslre.utils.AESUtil;
import org.deslre.utils.Results;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.deslre.utils.StringUtil.isEmpty;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    private UserLoginRepository userLoginRepository;

    @Override
    public Results<List<UserLogin>> finAll() {
        List<UserLogin> loginList = userLoginRepository.findAll();
        return Results.ok(loginList);
    }

    @Override
    public Results<String> register(UserLogin userLogin) {
        if (isEmpty(userLogin.getUserName()) || isEmpty(userLogin.getPassWord())) {
            return Results.fail("账号或密码为空");
        }
        try {
            // 加密用户名和密码
            String encryptedUserName = AESUtil.encrypt(userLogin.getUserName());
            String encryptedPassWord = AESUtil.encrypt(userLogin.getPassWord());

            // 检查用户名是否已经存在
            if (userLoginRepository.findByUserName(encryptedUserName) != null) {
                return Results.fail("用户名已存在");
            }

            UserLogin user = new UserLogin();
            user.setUserName(encryptedUserName);
            user.setPassWord(encryptedPassWord);
            user.setExist(1);
            userLoginRepository.save(user);
            return Results.ok("用户注册成功!");
        } catch (Exception e) {
            return Results.fail("注册过程中的错误: " + e.getMessage());
        }
    }

    @Override
    public Results<String> loginUSer(UserLogin userLogin) {
        if (isEmpty(userLogin.getUserName()) || isEmpty(userLogin.getPassWord())) {
            return Results.fail("账号或密码为空");
        }

        try {
            String encryptedUsername = AESUtil.encrypt(userLogin.getUserName());
            String encryptedPassword = AESUtil.encrypt(userLogin.getPassWord());

            Optional<UserLogin> optionalUserLogin = userLoginRepository.findByUserNameAndPassWord(encryptedUsername, encryptedPassword);

            if (optionalUserLogin.isPresent()) {
                return Results.ok("登录成功！");
            } else {
                return Results.fail("用户名或密码错误！");
            }
        } catch (Exception e) {
            return Results.fail("登录失败：" + e.getMessage());
        }
    }

}
