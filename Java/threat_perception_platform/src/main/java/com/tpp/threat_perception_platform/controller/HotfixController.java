package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HotfixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threats/hotfix")
public class HotfixController {

    @Autowired
    private HotfixService hotfixService;

    @RequestMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return hotfixService.list(param);
    }
}
