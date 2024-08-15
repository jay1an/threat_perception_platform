package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.pojo.Process;

import java.util.List;

public interface ProcessService {
    void processProcesses(List<Process> processes);

    ResponseResult<?> list(MyParam param);
}
