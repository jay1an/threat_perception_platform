package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.HostWeakPwdMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostWeakPwd;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostWeakPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HostWeakPwdServiceImpl implements HostWeakPwdService {

    @Autowired
    private HostWeakPwdMapper hostWeakPwdMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processHostWeakPwds(List<HostWeakPwd> hostWeakPwds) {
        String mac = hostWeakPwds.get(0).getMac();
        // 删除该主机的所有弱口令
        hostWeakPwdMapper.deleteByMac(mac);
        // 批量插入
        hostWeakPwdMapper.insertBatch(hostWeakPwds);
    }
    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<HostWeakPwd> hostWeakPwds = hostWeakPwdMapper.findAll(param);
        PageInfo<HostWeakPwd> pageInfo = new PageInfo<>(hostWeakPwds);
        return new ResponseResult<>(pageInfo.getTotal(), hostWeakPwds);
    }

    @Override
    public void deleteByMac(String mac) {
        hostWeakPwdMapper.deleteByMac(mac);
    }
}
