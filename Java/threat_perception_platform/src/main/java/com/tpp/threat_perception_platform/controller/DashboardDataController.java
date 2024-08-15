package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.dto.DashboardDataDTO;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.DashboardDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardDataController {

    @Autowired
    private DashboardDataService dashboardDataService;

    @PostMapping("/data")
    public ResponseResult<?> getDashboardData()
    {
        return dashboardDataService.getDashboardData();
    }
}
