package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostBaseline;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface HostBaselineService {

    void processHostBaselines(List<HostBaseline> hostBaselines);

    ResponseResult<?> list(MyParam param);

}
