package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.ProcessMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Process;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;
    @Override
    public void processProcesses(List<Process> processes) {
        // 删除旧的
        String mac = processes.get(0).getMac();
        processMapper.deleteByMac(mac);
        // 添加新的
        processMapper.insertBatch(processes);
    }
    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<Process> processes = processMapper.findAll(param);
        PageInfo<Process> pageInfo= new PageInfo<>(processes);
        return new ResponseResult<>(pageInfo.getTotal(), processes);
    }
}
