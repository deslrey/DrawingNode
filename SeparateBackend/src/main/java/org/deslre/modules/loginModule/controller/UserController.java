package org.deslre.modules.loginModule.controller;

import org.deslre.common.dto.LoginFormDTO;
import org.deslre.common.helper.JwtHelper;
import org.deslre.common.result.Results;
import org.deslre.modules.loginModule.entity.User;
import org.deslre.modules.loginModule.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Results<Map<String, Object>> login(@RequestBody LoginFormDTO loginFormDTO) {
//        Results<String> login = userService.login(loginFormDTO);
        String encrypt = JwtHelper.createToken(loginFormDTO.getUsername(), loginFormDTO.getPassword());
        System.out.println("encrypt = " + encrypt);
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("token", encrypt);
        return Results.ok(responseData);
    }


    @GetMapping(value = "/info")
    public Results<Map<String, Object>> info() {
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("roles", "admin");
        responseData.put("name", "Super admin");
        responseData.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Results.ok(responseData);
    }

    @PostMapping("/logout")
    public Results<String> logout() {
        return Results.ok("退出成功");
    }

    @GetMapping("/getAllUser")
    public Results<List<User>> getAllUser() {
        return userService.getAllUser();
    }

//    @PostMapping("/login")
//    public Results<String> login(@RequestBody LoginFormDTO loginFormDTO) {
//        return userService.login(loginFormDTO);
//    }
//
//    @GetMapping("/test")
//    public Results<String> ok() {
//        return Results.ok("Hello");
//    }
//
//    @GetMapping("/curr-user")
//    public Results<LoginFormDTO> currUser(@RequestHeader(FinalUtil.HEADER_TOKEN_NAME) String clientToken) {
//        return userService.currUser(clientToken);
//    }
//
//    @DeleteMapping("/logout")
//    public Results<String> logout(@RequestHeader(FinalUtil.HEADER_TOKEN_NAME) String clientToken) {
//        //从redis移除token
//        stringRedisTemplate.delete(clientToken);
//        return Results.ok();
//    }


}
