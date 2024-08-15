package com.tpp.threat_perception_platform.controller;


import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @RequestMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return processService.list(param);
    }
}
