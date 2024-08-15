package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostBaseline;

import java.util.List;

/**
* @author 23351
* @description 针对表【host_baseline】的数据库操作Mapper
* @createDate 2024-06-22 15:17:58
* @Entity com.tpp.threat_perception_platform.pojo.HostBaseline
*/
public interface HostBaselineMapper {

    int deleteByPrimaryKey(Long id);

    int insert(HostBaseline record);

    int insertSelective(HostBaseline record);

    HostBaseline selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HostBaseline record);

    int updateByPrimaryKey(HostBaseline record);

    int deleteByMac(String mac);

    int insertBatch(List<HostBaseline> hostBaselines);

    List<HostBaseline> findAll(MyParam param);

    int selectCount();

    int selectAbnormalCount();

    List<String> selectAbnormalHosts();

}
