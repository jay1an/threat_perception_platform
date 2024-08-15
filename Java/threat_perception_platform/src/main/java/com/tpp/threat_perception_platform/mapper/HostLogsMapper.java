package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostLogs;

import java.util.List;

/**
* @author 23351
* @description 针对表【host_logs】的数据库操作Mapper
* @createDate 2024-06-20 20:21:44
* @Entity com.tpp.threat_perception_platform.pojo.HostLogs
*/
public interface HostLogsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(HostLogs record);

    int insertSelective(HostLogs record);

    HostLogs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HostLogs record);

    int updateByPrimaryKey(HostLogs record);

    int deleteByMac(String mac);

    int insertBatch(List<HostLogs> hostLogsList);

    List<HostLogs> findAll(MyParam param);

    int selectCount();

    List<HostLogs> selectAllByEventIds(MyParam param,Integer[] eventIds);

}
