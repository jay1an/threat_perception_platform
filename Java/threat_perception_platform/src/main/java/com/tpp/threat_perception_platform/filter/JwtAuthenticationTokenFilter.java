package com.tpp.threat_perception_platform.filter;

import com.alibaba.fastjson.JSON;
import com.tpp.threat_perception_platform.pojo.LoginUser;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.utils.JwtUtil;
import com.tpp.threat_perception_platform.utils.RedisCache;
import com.tpp.threat_perception_platform.utils.WebUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("Authorization");
        // token是空则放行
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String uuid = "";
        try {
            Claims claims = JwtUtil.parseJWT(token);
            uuid = claims.getSubject();
            // 从redis中获取用户信息
            String redisKey = "login_" + uuid;
            LoginUser loginUser = JSON.parseObject(redisCache.getCacheObject(redisKey), LoginUser.class);
            System.out.println(loginUser);
            if (Objects.isNull(loginUser)) {
                WebUtils.renderString(response, JSON.toJSONString(new ResponseResult<Object>(1002, "用户未登录！")));
                return;
            }
            // 获取权限信息封装到Authentication中
            UsernamePasswordAuthenticationToken authenticationToken = // 第三个参数需要传递authorities
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            // 存入SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 放行
            // 查看是否需要续期token
            if(JwtUtil.isTokenExpiredSoon(token)){
                try {
//                  创建一个新的jwt，更新了时间
                    String newJwt = JwtUtil.createJWT(uuid,JwtUtil.JWT_TTL);
                    // 在缓存中刷新缓存消息的ttl
                    redisCache.expire(redisKey,60*60, TimeUnit.SECONDS);
                    System.out.println("token已更新");
                    // 将新的token传到前端
                    response.setHeader("token",newJwt);
                } catch (Exception e) {
                    WebUtils.renderString(response, JSON.toJSONString(new ResponseResult<Object>(-1, "token续期失败！")));
                }
            }
            // 放行
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            WebUtils.renderString(response, JSON.toJSONString(new ResponseResult<Object>(1002, "token非法！请重新登录！")));
        }

    }




}

