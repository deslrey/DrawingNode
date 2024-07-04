package org.deslre.loginModule.service.impl;

import org.deslre.loginModule.entity.UserLogin;
import org.deslre.loginModule.repository.UserLoginRepository;
import org.deslre.loginModule.service.UserLoginService;
import org.deslre.utils.ResultCodeEnum;
import org.deslre.utils.Results;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public Results<UserLogin> save(String userName, String passWord) {
        if (isEmpty(userName) || isEmpty(passWord)) {
            return Results.fail(ResultCodeEnum.USERNAME_PASSWORD_EMPTY);
        }




        return Results.ok();

    }
}
