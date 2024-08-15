package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.HostAppThreatMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostAppThreat;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostAppThreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostAppThreatServiceImpl implements HostAppThreatService {

    @Autowired
    private HostAppThreatMapper hostAppThreatMapper;

    @Override
    public void processHostAppThreats(List<HostAppThreat> hostAppThreats) {
        // 删除旧的
        String mac = hostAppThreats.get(0).getMac();
        hostAppThreatMapper.deleteByMac(mac);
        // 添加新的
        hostAppThreatMapper.insertBatch(hostAppThreats);
    }

    @Override
    public void deleteByMac(String mac) {
        hostAppThreatMapper.deleteByMac(mac);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(),param.getLimit());
        List<HostAppThreat> hostAppThreats = hostAppThreatMapper.findAll(param);
        PageInfo<HostAppThreat> pageInfo = new PageInfo<>(hostAppThreats);
        return new ResponseResult<>(pageInfo.getTotal(),hostAppThreats);
    }
}
