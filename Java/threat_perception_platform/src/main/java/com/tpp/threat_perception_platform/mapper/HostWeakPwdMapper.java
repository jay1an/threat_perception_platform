package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostWeakPwd;

import java.util.List;

/**
* @author 23351
* @description 针对表【host_weak_pwd】的数据库操作Mapper
* @createDate 2024-06-18 16:37:01
* @Entity com.tpp.threat_perception_platform.pojo.HostWeakPwd
*/
public interface HostWeakPwdMapper {

    int deleteByPrimaryKey(Long id);

    int insert(HostWeakPwd record);

    int insertSelective(HostWeakPwd record);

    HostWeakPwd selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HostWeakPwd record);

    int updateByPrimaryKey(HostWeakPwd record);

    int deleteByMac(String mac);

    int insertBatch(List<HostWeakPwd> hostWeakPwds);

    List<HostWeakPwd> findAll(MyParam param);

    List<String> selectAbnormalHosts();

    int selectCount();
}
