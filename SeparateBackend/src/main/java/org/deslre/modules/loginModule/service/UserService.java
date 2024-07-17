package org.deslre.modules.loginModule.service;

import org.deslre.common.dto.LoginFormDTO;
import org.deslre.common.helper.JwtHelper;
import org.deslre.common.result.ResultCodeEnum;
import org.deslre.common.result.Results;
import org.deslre.common.utils.FinalUtil;
import org.deslre.common.utils.MD5;
import org.deslre.modules.loginModule.entity.User;
import org.deslre.modules.loginModule.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static org.deslre.common.utils.StringUtil.isEmpty;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Results<String> login(LoginFormDTO loginFormDTO) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(loginFormDTO.getVerificationCode()))) {
            return Results.fail("验证码错误");
        }

        if (loginFormDTO.getVerificationCode() != null) {
            String code = stringRedisTemplate.opsForValue().get(FinalUtil.IMAGE_PATH);
            if (code == null) {
                return Results.fail("验证码失效");
            }
            if (!MD5.encrypt(loginFormDTO.getVerificationCode()).equals(code)) {
                return Results.fail("验证码错误");
            }
        }

        if (isEmpty(loginFormDTO.getUsername()) || isEmpty(loginFormDTO.getPassword())) {
            return Results.fail("账号/密码为空");
        }

        User user = userRepository.findUserByNickName(loginFormDTO.getUsername());
        if (user == null) {
            return Results.fail("用户不存在");
        }
        String passWord = MD5.encrypt(loginFormDTO.getPassword());
        if (passWord.equals(user.getPassWord())) {
            String token = JwtHelper.createToken(user.getNickName(), user.getPassWord());
            return Results.ok(token);
        } else {
            return Results.fail(ResultCodeEnum.PASSWORD_ERROR);
        }

    }

    public Results<LoginFormDTO> currUser(String clientToken) {
        String userName = JwtHelper.getUserName(clientToken);
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        loginFormDTO.setUsername(userName);
        return Results.ok(loginFormDTO);
    }

    public Results< List<User>> getAllUser() {
        List<User> list = userRepository.findAll();
        return Results.ok(list);
    }
}
