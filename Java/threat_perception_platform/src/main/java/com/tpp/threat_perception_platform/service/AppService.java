package com.tpp.threat_perception_platform.service;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.App;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface AppService {
    void processApps(List<App> apps);

    ResponseResult<?> list(MyParam param);
}
