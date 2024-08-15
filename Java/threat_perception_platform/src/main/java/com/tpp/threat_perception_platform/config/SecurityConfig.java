package com.tpp.threat_perception_platform.config;

import com.tpp.threat_perception_platform.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 创建BCryptPasswordEncoder注入容器
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 创建AuthenticationManager注入容器
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 解决 in a frame because it set 'X-Frame-Options' to 'deny'
                .headers(head -> {
                    head.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                    head.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable);
                })
                // CSRF禁用，因为不使用session
                .csrf(AbstractHttpConfigurer::disable)
                // 不通过Session获取SecurityContext
                .sessionManagement(sess -> {
                    sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 过滤请求
                .authorizeHttpRequests(auth -> {
                    // 对于登录接口 允许匿名访问
                    auth.requestMatchers("/user/login").anonymous(); // 可以匿名访问这两个网站（不登陆即可访问）
                    auth.requestMatchers("/page/login").anonymous();
                    // CSS/JS/IMG/LIB/FONTS放行  静态资源
                    auth.requestMatchers("/css/**").anonymous();
                    auth.requestMatchers("/js/**").anonymous();
                    auth.requestMatchers("/img/**").anonymous();
                    auth.requestMatchers("/imgs/**").anonymous();
                    auth.requestMatchers("/lib/**").anonymous();
                    auth.requestMatchers("/fonts/**").anonymous();
                    auth.requestMatchers("/layui/**").anonymous();
                    auth.requestMatchers("/").anonymous();
                    // 退出登录接口
                    auth.requestMatchers("/user/logout").permitAll();   // 注册成功之后每人都可以登出
                    // 登陆成功可以页面路由
                    auth.requestMatchers("/page/**").permitAll();
                    // 对于特定的路径配置权限
                    auth.requestMatchers("/host/delete").hasAuthority("ADMIN");
                    auth.requestMatchers("/host/setSyncLogsOff").hasAnyAuthority("ADMIN","LOGS");
                    auth.requestMatchers("/host/setSyncLogsOn").hasAnyAuthority("ADMIN","LOGS");
                    auth.requestMatchers("/host/threats/discovery").hasAnyAuthority("ADMIN","THREAT");
                    // 通配符路径
                    auth.requestMatchers("/role/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/user/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/opra_log/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/app_threat_db/**").hasAnyAuthority("ADMIN","THREAT");
                    auth.requestMatchers("/vulnerability/**").hasAnyAuthority("ADMIN","THREAT");
                    auth.requestMatchers("/threats/**").hasAnyAuthority("ADMIN","THREAT");
                    auth.requestMatchers("/logs/**").hasAnyAuthority("ADMIN","LOGS");
                    auth.requestMatchers("/baseline/**").hasAnyAuthority("ADMIN","BASELINE");
                    auth.requestMatchers( "/host/assets/**").permitAll();
                    // 除匿名请求外全部需要鉴权认证
                    auth.anyRequest().authenticated();
                });
        // 把token校验过滤器添加到过滤器链中
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
