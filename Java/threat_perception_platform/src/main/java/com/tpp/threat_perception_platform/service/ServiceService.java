package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Service;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface ServiceService {

    void processServices(List<Service> services);

    ResponseResult<?> list(MyParam param);

    List<Service> findHostServiceByKeywords(String keywords,String mac);
}
