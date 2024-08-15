package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.AppThreatsDbMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppThreatsDb;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AppThreatsDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AppThreatsDbServiceImpl implements AppThreatsDbService {


    @Autowired
    private AppThreatsDbMapper appThreatsDbMapper;


    @Override
    public List<AppThreatsDb> findByKeywords(String keywords) {
        return appThreatsDbMapper.findByKeywords(keywords);
    }

    @Override
    public ResponseResult<?> save(AppThreatsDb appThreatsDb) {
        // 查看是否有相同数据
        AppThreatsDb appThreatsDb1 = appThreatsDbMapper.findByName(appThreatsDb.getName());
        if (appThreatsDb1 != null) {
            return new ResponseResult<>(-1, "该数据已存在");
        }
        appThreatsDbMapper.insert(appThreatsDb);
        return new ResponseResult<>(0, "保存成功");
    }

    @Override
    public ResponseResult<?> edit(AppThreatsDb appThreatsDb) {
        if(appThreatsDbMapper.updateByPrimaryKeySelective(appThreatsDb)>0){
            return new ResponseResult<>(0, "修改成功");
        }
        return new ResponseResult<>(-1, "修改失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<?> delete(Integer[] ids) {
        if(appThreatsDbMapper.deleteByIds(ids)==ids.length){
            return new ResponseResult<>(0,"删除成功");
        }
        return new ResponseResult<>(-1,"删除失败");
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<AppThreatsDb> appThreatsDbs = appThreatsDbMapper.list(param);
        PageInfo<AppThreatsDb> pageinfo = new PageInfo<>(appThreatsDbs);
        return new ResponseResult<>(pageinfo.getTotal(), appThreatsDbs);
    }
}
