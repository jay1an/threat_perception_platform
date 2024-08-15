package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.AppMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.App;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public void processApps(List<App> apps) {
        // 删除旧的
        String mac = apps.get(0).getMac();
        appMapper.deleteByMac(mac);
        // 加入新的
        appMapper.insertBatch(apps);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<App> apps = appMapper.findAll(param);
        PageInfo<App> pageInfo = new PageInfo<>(apps);
        return new ResponseResult<>(pageInfo.getTotal(), apps);
    }
}
