package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostLogs;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.apache.coyote.Response;

import java.util.List;

public interface HostLogsService {

    void processHostLogs(List<HostLogs> hostLogsList);

    ResponseResult<?> list(MyParam param);

    ResponseResult<?> listLoginLogs(MyParam param);

    ResponseResult<?> listAccountLogs(MyParam param);
}
