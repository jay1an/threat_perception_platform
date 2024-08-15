package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.HostLogsMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostLogs;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostLogsServiceImpl implements HostLogsService {

    @Autowired
    private HostLogsMapper hostLogsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processHostLogs(List<HostLogs> hostLogsList) {
        // 删除旧的
        String mac = hostLogsList.get(0).getMac();
        hostLogsMapper.deleteByMac(mac);
        // 插入新的
        hostLogsMapper.insertBatch(hostLogsList);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<HostLogs> hostLogsList = hostLogsMapper.findAll(param);
        PageInfo<HostLogs> pageInfo = new PageInfo<>(hostLogsList);
        return new ResponseResult<>(pageInfo.getTotal(), hostLogsList);
    }

    @Override
    public ResponseResult<?> listLoginLogs(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Integer[] loginEventIds = new Integer[]{4624,4625}; // 4624登陆成功   4625登陆失败
        List<HostLogs> hostLogsList = hostLogsMapper.selectAllByEventIds(param,loginEventIds);
        PageInfo<HostLogs> pageInfo = new PageInfo<>(hostLogsList);
        return new ResponseResult<>(pageInfo.getTotal(), hostLogsList);
    }

    @Override
    public ResponseResult<?> listAccountLogs(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        Integer[] accountEventIds = new Integer[]{4720, 4726, 4726, 4723}; //  4720:用户被创建 4726:用户被删除 4723:更改密码
        List<HostLogs> hostLogsList = hostLogsMapper.selectAllByEventIds(param,accountEventIds);
        PageInfo<HostLogs> pageInfo = new PageInfo<>(hostLogsList);
        return new ResponseResult<>(pageInfo.getTotal(), hostLogsList);
    }
}
