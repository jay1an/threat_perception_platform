package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.dto.DashboardDataDTO;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.apache.coyote.Response;

public interface DashboardDataService {

    ResponseResult<?> getDashboardData();

}
