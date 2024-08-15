package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppThreatsDb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 23351
* @description 针对表【app_threats_db】的数据库操作Mapper
* @createDate 2024-06-19 14:20:48
* @Entity com.tpp.threat_perception_platform.pojo.AppThreatsDb
*/
public interface AppThreatsDbMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AppThreatsDb record);

    int insertSelective(AppThreatsDb record);

    AppThreatsDb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppThreatsDb record);

    int updateByPrimaryKey(AppThreatsDb record);

    List<AppThreatsDb> findByKeywords(String keywords);

    AppThreatsDb findByName(String name);

    int deleteByIds(@Param("ids") Integer[] ids);

    List<AppThreatsDb> list(MyParam param);


}
