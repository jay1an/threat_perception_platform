package com.tpp.threat_perception_platform.controller;


import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaseLineTask;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaseLineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baseline")
public class BaseLineController {

    @Autowired
    private BaseLineTaskService baseLineTaskService;

    @RequestMapping("/task/list")
    public ResponseResult<?> listTask(MyParam param) {
        return baseLineTaskService.list(param);
    }

    @RequestMapping("/task/add")
    @MyLog(title = "基线任务模块", content = "添加基线任务")
    public ResponseResult<?> addTask(@RequestBody BaseLineTask task) {
        return baseLineTaskService.add(task);
    }

    @RequestMapping("/task/edit")
    @MyLog(title = "基线任务模块", content = "修改基线任务")
    public ResponseResult<?> editTask(@RequestBody BaseLineTask task) {
        return baseLineTaskService.edit(task);
    }

    @RequestMapping("/task/delete")
    @MyLog(title = "基线任务模块", content = "删除基线任务")
    public ResponseResult<?> deleteTask(@RequestParam("ids[]")  Integer[] ids) {
        return baseLineTaskService.delete(ids);
    }

}
