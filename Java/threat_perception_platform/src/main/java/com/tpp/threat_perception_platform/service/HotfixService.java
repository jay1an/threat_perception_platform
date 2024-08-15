package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface HotfixService {

    void processHotfixes(List<Hotfix> hotfixes);

    ResponseResult<?> list(MyParam param);
}
