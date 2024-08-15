package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Process;

import java.util.List;

/**
* @author 23351
* @description 针对表【process】的数据库操作Mapper
* @createDate 2024-06-13 14:02:46
* @Entity com.tpp.threat_perception_platform.pojo.Process
*/
public interface ProcessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Process record);

    int insertSelective(Process record);

    Process selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Process record);

    int updateByPrimaryKey(Process record);

    int deleteByMac(String mac);

    int insertBatch(List<Process> processes);

    List<Process> findAll(MyParam param);

    int selectCount();

}
