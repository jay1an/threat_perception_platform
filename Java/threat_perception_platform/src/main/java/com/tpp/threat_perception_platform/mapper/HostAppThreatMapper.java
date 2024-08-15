package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostAppThreat;

import java.util.List;

/**
* @author 23351
* @description 针对表【host_app_threat】的数据库操作Mapper
* @createDate 2024-06-19 16:40:26
* @Entity com.tpp.threat_perception_platform.pojo.HostAppThreat
*/
public interface HostAppThreatMapper {

    int deleteByPrimaryKey(Long id);

    int insert(HostAppThreat record);

    int insertSelective(HostAppThreat record);

    HostAppThreat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HostAppThreat record);

    int updateByPrimaryKey(HostAppThreat record);

    int insertBatch(List<HostAppThreat> hostAppThreats);

    int deleteByMac(String mac);

    List<HostAppThreat> findAll(MyParam param);

    List<String> selectAbnormalHosts();

    int selectCount();

}
