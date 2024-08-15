package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 23351
* @description 针对表【host】的数据库操作Mapper
* @createDate 2024-06-07 12:31:26
* @Entity com.tpp.threat_perception_platform.pojo.Host
*/

public interface HostMapper {

    int deletebyIds(@Param("ids") Integer[] id);

    int deleteByPrimaryKey(Long id);

    int insert(Host record);

    int insertSelective(Host record);

    Host selectByPrimaryKey(Long id);

    Host selectByMac(String mac);

    List<Host> findAll(MyParam param);

    int updateByPrimaryKeySelective(Host record);

    int updateByPrimaryKey(Host record);

    int updateByMacSelective(Host record);

    List<Host> findAllOnlyWithMacAndHostname();

    int selectCountbyStatus(Integer status);

    List<String> selectOsName();

}
