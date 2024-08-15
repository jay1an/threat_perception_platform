package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.BaseLineTaskMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaseLineTask;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaseLineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseLineTaskServiceImpl implements BaseLineTaskService {

    @Autowired
    private BaseLineTaskMapper baseLineTaskMapper;

    @Override
    public List<BaseLineTask> findUnfinishedTasks() {
        return baseLineTaskMapper.selectByStatus(0);
    }

    @Override
    public void setTaskStatusCompleted(BaseLineTask task) {
        BaseLineTask baseLineTask = new BaseLineTask();
        baseLineTask.setStatus(1);
        baseLineTask.setId(task.getId());
        baseLineTaskMapper.updateByPrimaryKeySelective(baseLineTask);
    }

    @Override
    public ResponseResult<?> add(BaseLineTask task) {
        baseLineTaskMapper.insert(task);
        return new ResponseResult<>(0, "添加成功");
    }

    @Override
    public ResponseResult<?> delete(Integer[] ids) {
        baseLineTaskMapper.deleteByIds(ids);
        return new ResponseResult<>(0, "删除成功");
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<BaseLineTask> tasks = baseLineTaskMapper.findAll(param);
        PageInfo<BaseLineTask> pageInfo = new PageInfo<>(tasks);
        return new ResponseResult<>(pageInfo.getTotal(), tasks);
    }

    @Override
    public ResponseResult<?> edit(BaseLineTask task) {
        baseLineTaskMapper.updateByPrimaryKey(task);
        return new ResponseResult<>(0, "修改成功");
    }
}
