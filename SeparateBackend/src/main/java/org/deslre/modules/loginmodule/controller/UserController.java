package org.deslre.modules.loginmodule.controller;

import org.deslre.common.dto.LoginFormDTO;
import org.deslre.common.result.Results;
import org.deslre.common.utils.FinalUtil;
import org.deslre.modules.loginmodule.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Results<String> login(@RequestBody LoginFormDTO loginFormDTO) {
        return userService.login(loginFormDTO);
    }

    @GetMapping("/curr-user")
    public Results<LoginFormDTO> currUser(@RequestHeader(FinalUtil.HEADER_TOKEN_NAME) String clientToken) {
        return userService.currUser(clientToken);
    }

    @DeleteMapping("/logout")
    public Results<String> logout(@RequestHeader(FinalUtil.HEADER_TOKEN_NAME) String clientToken) {
        //从redis移除token
        stringRedisTemplate.delete(clientToken);
        return Results.ok();
    }


}
