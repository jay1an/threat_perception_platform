package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.UserService;
import com.tpp.threat_perception_platform.service.VulnerabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关的接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户列表
     * @param param
     * @return
     */
    @PostMapping("/list")
    public ResponseResult<?> userList(MyParam param){
        return userService.userList(param);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/save")
    @MyLog(title = "用户模块", content = "新增用户")
    public ResponseResult<?> userSave(@RequestBody User user){
        return userService.save(user);
    }
    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PostMapping("/edit")
    @MyLog(title = "用户模块", content = "修改用户")
    public ResponseResult<?> userEdit(@RequestBody User user){
        return userService.edit(user);
    }

    /**
     * 删除用户
     * @param
     * @return
     */
    @PostMapping("/delete")
    @MyLog(title = "用户模块", content = "删除用户")
    public ResponseResult<?> delete(@RequestParam("ids[]") Integer[] ids){
        return userService.delete(ids);
    }


}
