package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.OperLog;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface OperLogService {

    void add(OperLog operLog);

    void deleteByPrimaryKey(Long id);

    ResponseResult<?> list(MyParam param);

    ResponseResult<?> delete(Integer[] ids);

}
