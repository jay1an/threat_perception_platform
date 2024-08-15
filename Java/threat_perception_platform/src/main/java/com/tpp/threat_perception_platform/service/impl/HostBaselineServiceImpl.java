package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.HostBaselineMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostBaseline;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostBaselineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostBaselineServiceImpl implements HostBaselineService {

    @Autowired
    private HostBaselineMapper hostBaselineMapper;

    @Override
    public void processHostBaselines(List<HostBaseline> hostBaselines) {
        // 删除旧的
        String mac = hostBaselines.get(0).getMac();
        hostBaselineMapper.deleteByMac(mac);
        // 批量添加新的
        hostBaselineMapper.insertBatch(hostBaselines);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<HostBaseline> hostBaselines = hostBaselineMapper.findAll(param);
        PageInfo<HostBaseline> pageInfo = new PageInfo<>(hostBaselines);
        return new ResponseResult<>(pageInfo.getTotal(), hostBaselines);
    }
}
