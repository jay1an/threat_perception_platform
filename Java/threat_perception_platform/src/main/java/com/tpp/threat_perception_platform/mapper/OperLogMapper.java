package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.OperLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 23351
* @description 针对表【oper_log(操作日志记录)】的数据库操作Mapper
* @createDate 2024-06-27 16:42:43
* @Entity com.tpp.threat_perception_platform.pojo.OperLog
*/
public interface OperLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OperLog record);

    int insertSelective(OperLog record);

    OperLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperLog record);

    int updateByPrimaryKey(OperLog record);

    List<OperLog> findAll(MyParam param);

    void deleteByIds(@Param("ids") Integer[] ids);

}
