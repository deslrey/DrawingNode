package org.deslre.common.utils;


import org.deslre.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

//获取当前用户信息工具类
public class AuthContextHolder {

    //获取当前用户id
//    public static Long getUserId(HttpServletRequest request) {
//        //从header获取token
//        String token = request.getHeader("token");
//        //jwt从token获取userid
//        return JwtHelper.getUserId(token);
//    }

    //获取当前用户名称
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userid
        return JwtHelper.getUserName(token);
    }
}
