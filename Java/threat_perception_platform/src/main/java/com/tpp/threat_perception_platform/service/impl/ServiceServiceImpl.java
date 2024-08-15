package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.ServiceMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Service;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processServices(List<Service> services) {
        String mac = services.get(0).getMac();
        // 先删除所有的以前的服务
        serviceMapper.deleteByMac(mac);
        // 添加新的
        serviceMapper.insertBatch(services);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<Service> services = serviceMapper.findAll(param);
        PageInfo<Service> pageInfo = new PageInfo<>(services);
        return new ResponseResult<>(pageInfo.getTotal(), services);
    }

    @Override
    public List<Service> findHostServiceByKeywords(String keywords,String mac) {
        return serviceMapper.findHostServiceByKeywords(keywords,mac);
    }
}
