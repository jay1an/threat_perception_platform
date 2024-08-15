package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.ThreatsParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.apache.coyote.Response;

import java.util.List;

public interface HostService {
    int save(Host host);

    // 改和增由agent自动实现
    // 只需要实现查的业务逻辑 和 删除
    ResponseResult<?> list(MyParam param);

    // 不分页，返回Host的mac和hostname
    ResponseResult<?> list();

    ResponseResult<?> delete(Integer[] ids);

    void updateStatusOnline(String mac);

    void updateStatusOffline(String mac);

    ResponseResult<?> setSyncLogsOn(String mac);

    ResponseResult<?> setSyncLogsOff(String mac);

    List<Host> findOnlineHosts();

    List<Host> findOnlineHostsAndSyncLogsOn();

    ResponseResult<?> assetsDetection(AssetsParam param);

    ResponseResult<?> threatsDetection(ThreatsParam param);

    ResponseResult<?> hostDetails(String mac);
}
