package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Service;

import java.util.List;

/**
* @author 23351
* @description 针对表【service】的数据库操作Mapper
* @createDate 2024-06-13 12:59:26
* @Entity com.tpp.threat_perception_platform.pojo.Service
*/
public interface ServiceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Service record);

    int insertSelective(Service record);

    Service selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Service record);

    int updateByPrimaryKey(Service record);

    int deleteByMac(String mac);

    int insertBatch(List<Service> services);

    List<Service> findAll(MyParam param);

    List<Service> findHostServiceByKeywords(String keywords,String mac);

    int selectCount();

}
