package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaseLineTask;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.apache.coyote.Response;

import java.util.List;

public interface BaseLineTaskService {

    List<BaseLineTask> findUnfinishedTasks();

    void setTaskStatusCompleted(BaseLineTask task);

     ResponseResult<?> add(BaseLineTask task);

     ResponseResult<?> delete(Integer[] ids);

     ResponseResult<?> list(MyParam param);

     ResponseResult<?> edit(BaseLineTask task);

}
