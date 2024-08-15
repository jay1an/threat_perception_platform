package com.tpp.threat_perception_platform.controller;


import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/opra_log")
public class OperLogController {

    @Autowired
    private OperLogService operLogService;


    @PostMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return operLogService.list(param);
    }

    @PostMapping("/delete")
    @MyLog(title = "操作日志模块", content = "删除操作日志")
    public ResponseResult<?> delete(@RequestParam("ids[]") Integer[] ids){
       return operLogService.delete(ids);
    }

}
