package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostWeakPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threats/weak_pwd")
public class HostWeakPwdController {

    @Autowired
    private HostWeakPwdService hostWeakPwdService;

    @PostMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return hostWeakPwdService.list(param);
    }

}
