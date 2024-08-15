package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostWeakPwd;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface HostWeakPwdService {

    void processHostWeakPwds(List<HostWeakPwd> hostWeakPwds);

    ResponseResult<?> list(MyParam param);

    void deleteByMac(String mac);
}
