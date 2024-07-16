package org.deslre.modules.loginmodule.controller;

import org.deslre.common.dto.LoginFormDTO;
import org.deslre.common.result.Results;
import org.deslre.modules.loginmodule.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Results<String> login(@RequestBody LoginFormDTO loginFormDTO) {
        return userService.login(loginFormDTO);
    }


}
