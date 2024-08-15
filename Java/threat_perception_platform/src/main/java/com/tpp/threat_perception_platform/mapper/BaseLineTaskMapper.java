package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaseLineTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 23351
* @description 针对表【base_line_task】的数据库操作Mapper
* @createDate 2024-06-21 10:46:27
* @Entity com.tpp.threat_perception_platform.pojo.BaseLineTask
*/
public interface BaseLineTaskMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BaseLineTask record);

    int insertSelective(BaseLineTask record);

    BaseLineTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaseLineTask record);

    int updateByPrimaryKey(BaseLineTask record);

    List<BaseLineTask> selectByStatus(Integer status);

    int deleteByIds(@Param("ids") Integer[] ids);

    List<BaseLineTask> findAll(MyParam param);

    int selectUnfinishedCount();

}
