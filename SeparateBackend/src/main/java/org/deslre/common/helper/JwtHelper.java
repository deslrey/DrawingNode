package org.deslre.common.helper;

import io.jsonwebtoken.*;
import org.deslre.common.utils.StringUtil;

import java.util.Date;

public class JwtHelper {

    //过期时间
    private static final long tokenExpiration = 24 * 60 * 60 * 1000;
    //签名秘钥
    private static final String tokenSignKey = "123456";

    //根据参数生成token
    public static String createToken(String userName, String passWord) {
        return Jwts.builder()
                .setSubject("DESLRE-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userName", userName)
                .claim("passWord", passWord)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    //根据token字符串得到用户id
    public static String getPassWord(String token) {
        if (StringUtil.isEmpty(token)) return null;

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("passWord");
    }

    //根据token字符串得到用户名称
    public static String getUserName(String token) {
        if (StringUtil.isEmpty(token)) return "";

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken("12222", "张三");
        System.out.println(token);
        System.out.println(JwtHelper.getPassWord(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}

