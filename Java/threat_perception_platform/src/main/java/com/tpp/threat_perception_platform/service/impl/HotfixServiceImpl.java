package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.HotfixMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HotfixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotfixServiceImpl implements HotfixService {

    @Autowired
    private HotfixMapper hotfixMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processHotfixes(List<Hotfix> hotfixes) {
        // 先删除旧的
        String mac = hotfixes.get(0).getMac();
        hotfixMapper.deleteByMac(mac);
        // 插入新的
        hotfixMapper.insertBatch(hotfixes);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(param.getPage(), param.getLimit());
        List<Hotfix> hotfixes = hotfixMapper.findAll(param);
        PageInfo<Hotfix> pageInfo = new PageInfo<>(hotfixes);
        return new ResponseResult<>(pageInfo.getTotal(), hotfixes);
    }


}
