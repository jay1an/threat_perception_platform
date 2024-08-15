package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.App;

import java.util.List;

/**
* @author 23351
* @description 针对表【app】的数据库操作Mapper
* @createDate 2024-06-13 14:07:37
* @Entity com.tpp.threat_perception_platform.pojo.App
*/
public interface AppMapper {

    int deleteByPrimaryKey(Long id);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);

    int deleteByMac(String mac);

    int insertBatch(List<App> apps);

    List<App> findAll(MyParam param);

    int selectCount();

}
