package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class HostLogsController {

    @Autowired
    private HostLogsService hostLogsService;

    @PostMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return hostLogsService.list(param);
    }


    @PostMapping("/list/login")
    public ResponseResult<?> listLoginLogs(MyParam param) {
        return hostLogsService.listLoginLogs(param);
    }

    @PostMapping("/list/account")
    public ResponseResult<?> listAccountLogs(MyParam param) {
        return hostLogsService.listAccountLogs(param);
    }

}
