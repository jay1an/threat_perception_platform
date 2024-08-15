package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.OperLogMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.OperLog;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OperLogServiceImpl implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public void add(OperLog operLog) {
        operLogMapper.insert(operLog);
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        operLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<OperLog> operLogs = operLogMapper.findAll(param);
        PageInfo<OperLog> pageInfo = new PageInfo<>(operLogs);
        return new ResponseResult<>(pageInfo.getTotal(), operLogs);
    }

    @Override
    public ResponseResult<?> delete(Integer[] ids) {
        try {
            operLogMapper.deleteByIds(ids);
            return new ResponseResult<>(0, "删除成功");
        }
        catch (Exception e){
            return new ResponseResult<>(-1, "删除失败");
        }
    }


}
