package com.tpp.threat_perception_platform.service.impl;


import com.tpp.threat_perception_platform.mapper.UserMapper;
import com.tpp.threat_perception_platform.pojo.LoginUser;
import com.tpp.threat_perception_platform.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 认证
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    @Qualifier("rolePermissionsMap")
    private Map<Integer, String> rolePermissionsMap;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户数据
        User db_user = userMapper.selectByUserName(username);
        if (db_user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 判断状态
        if (db_user.getUserStatus() == 2) {
            throw new RuntimeException("用户被冻结！请联系超级管理员！");
        }
        // 这个地方还要获取权限信息
        String permissions = rolePermissionsMap.get(db_user.getUserRole());
        List<String> permissionList = Arrays.asList(permissions.split(","));

        // 把数据封装成LoginUser对象返回
        return new LoginUser(db_user.getUserName(), db_user.getUserPwd(), db_user.getId(), permissionList);
    }
}

