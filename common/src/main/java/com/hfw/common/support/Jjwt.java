package com.hfw.common.support;

import io.jsonwebtoken.*;
import lombok.Data;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author zyh
 * @date 2022-04-16
 */
@Data
public class Jjwt {
    public String jti;
    public String sub;
    //秘钥
    private String key;

    /**
     * 解析token, 解析错错误会抛出异常
     * @param token
     * @return
     */
    public Claims parse(String token){
        SecretKey key = new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * 生成token
     * @param claims claims是base64编码,可以被前端识别到,可以存用户信息
     * @param timeout 失效时间,单位分钟
     * @return
     */
    public String token(Map<String,Object> claims, int timeout){
        SecretKey key = new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, timeout);
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims) //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(jti) //jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
                .setIssuedAt(new Date()) //iat: jwt的签发时间
                .setSubject(sub) //sub: jwt所面向的用户
                .setExpiration(calendar.getTime())
                .signWith(key);
        return builder.compact();
    }

}
