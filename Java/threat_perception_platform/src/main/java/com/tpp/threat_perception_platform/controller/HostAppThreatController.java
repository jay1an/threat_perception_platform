package com.tpp.threat_perception_platform.controller;


import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostAppThreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threats/app")
public class HostAppThreatController {

    @Autowired
    private HostAppThreatService hostAppThreatService;

    @RequestMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return hostAppThreatService.list(param);
    }
}
