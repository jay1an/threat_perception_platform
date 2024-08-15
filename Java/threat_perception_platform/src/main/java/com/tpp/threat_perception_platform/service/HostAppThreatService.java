package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostAppThreat;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.apache.coyote.Response;

import java.util.List;

public interface HostAppThreatService {

    void processHostAppThreats(List<HostAppThreat> hostAppThreats);

    void deleteByMac(String mac);

    ResponseResult<?> list(MyParam param);

}
