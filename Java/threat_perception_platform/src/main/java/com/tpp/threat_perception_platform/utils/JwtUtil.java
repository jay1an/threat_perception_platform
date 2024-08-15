package com.tpp.threat_perception_platform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static java.lang.Thread.sleep;

/**
 * JWT工具类
 */
public class JwtUtil {

    //有效期为
    public static final Long JWT_TTL = 60 * 60 * 1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "thisIsJwtKey";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jtw
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jtw
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)      // 主题 可以是JSON数据
                .setIssuer("jay1an")         // 签发者
                .setIssuedAt(now)         // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 检验一下，如果还有三十分钟过期，则刷新token
     * 将新的token覆盖掉旧的（redis中要覆盖，然后也要传送到前端，前端也要覆盖）
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpiredSoon(String token) {
        try {
            Claims claims = JwtUtil.parseJWT(token);
            Date iat = claims.getIssuedAt(); // 获取JWT的签发时间
            long currentTimeMillis = System.currentTimeMillis(); // 获取当前时间的毫秒数

            // 阈值
            long refreshTime = JWT_TTL / (60*30);   // 每一分钟刷新一次
            return (currentTimeMillis - iat.getTime() ) >= refreshTime;
        } catch (Exception e) {
            // 如果在解析JWT时发生异常，可以假设令牌无效或已过期
            return true;
        }
    }

//    public static void main(String[] args) throws Exception {
//        String jwt = createJWT("jay1an", JWT_TTL);
//        System.out.println(jwt);
//        Claims claims = parseJWT(jwt);
//        System.out.println(claims);
//        System.out.println(claims.getSubject());
//        sleep(3000);
//        System.out.println(isTokenExpiredSoon(jwt.toString()));
//    }
}
