package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppThreatsDb;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface AppThreatsDbService {


    List<AppThreatsDb> findByKeywords(String keywords);

    ResponseResult<?> save(AppThreatsDb appThreatsDb);

    ResponseResult<?> edit(AppThreatsDb appThreatsDb);

    ResponseResult<?> delete(Integer[] ids);

    ResponseResult<?> list(MyParam param);
}
