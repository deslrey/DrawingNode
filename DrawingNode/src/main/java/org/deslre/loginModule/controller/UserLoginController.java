package org.deslre.loginModule.controller;


import org.deslre.loginModule.service.UserLoginService;
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


}
