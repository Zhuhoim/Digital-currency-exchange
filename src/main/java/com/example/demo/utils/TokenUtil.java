package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.UserEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {
    private static Map<String, UserEntity> tokenMap=new HashMap<>();
    private static String KEY="111111";

    /**
     * 生成token，存储token-user对应关系
     * 返回token令牌
     * @param UserEntity
     * @return
     */
    public static String generateToken(UserEntity user){
        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + (5*60 * 100000));
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username", user.getName())
                    .withClaim("password", user.getPassword()).withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        tokenMap.put(token,user);
        return token;
    }

    /**
     * 验证token是否合法
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public static UserEntity getUser(String token){
        return tokenMap.get(token);
    }

}
