package com.tpp.threat_perception_platform.controller;
import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    public ResponseResult<?> list(MyParam param){
        return roleService.list(param);
    }

    @PostMapping("/save")
    @MyLog(title = "角色模块", content = "添加角色")
    public ResponseResult<?> save(@RequestBody Role role){
        return roleService.save(role);
    }

    @PostMapping("/edit")
    @MyLog(title = "角色模块", content = "编辑角色")
    public ResponseResult<?> edit(@RequestBody Role role){
        return roleService.edit(role);
    }

    @PostMapping("/delete")
    @MyLog(title = "角色模块", content = "删除角色")
    public ResponseResult<?> delete(@RequestParam("ids[]") Integer[] ids){
        return roleService.delete(ids);
    }

    @PostMapping("/list/2")
    public ResponseResult<?> list(){
        return roleService.list();
    }

}
