package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.annotation.LoginLog;
import com.tpp.threat_perception_platform.annotation.LogoutLog;
import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.annotation.PasswordChangeLog;
import com.tpp.threat_perception_platform.dto.ChangePasswordRequest;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.LoginServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    LoginServcie loginService;

    /**
     * 登录接口
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/login")
    @LoginLog
    public ResponseResult<?> login(@RequestBody User user){
        return loginService.login(user);
    }

    /**
     * 登出接口
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/logout")
    @LogoutLog
    public ResponseResult<?> logout() {
        // 登出
        return loginService.logout();
    }

    @ResponseBody
    @RequestMapping("/user/change_password")
    @PasswordChangeLog
    public ResponseResult<?> changePassword(@RequestBody ChangePasswordRequest request) {
        // 登出
        return loginService.changePassword(request);
    }
}
