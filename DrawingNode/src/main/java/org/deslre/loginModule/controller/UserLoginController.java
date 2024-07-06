package org.deslre.loginModule.controller;


import org.deslre.loginModule.entity.UserLoginEntity;
import org.deslre.loginModule.service.UserLoginService;
import org.deslre.utils.Results;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-04
 */
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public Results<String> loginUser(@RequestBody UserLoginEntity userLoginEntity) {
        System.out.println("userLogin = " + userLoginEntity);
        return userLoginService.loginUSer(userLoginEntity);
    }

    @PostMapping("/register")
    public Results<String> register(@RequestBody UserLoginEntity userLoginEntity) {
        System.out.println("userLogin = " + userLoginEntity);
        return userLoginService.register(userLoginEntity);
    }

}
