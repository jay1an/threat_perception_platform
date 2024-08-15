package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;

import java.util.List;

/**
* @author 23351
* @description 针对表【hotfix】的数据库操作Mapper
* @createDate 2024-06-14 11:31:36
* @Entity com.tpp.threat_perception_platform.pojo.Hotfix
*/
public interface HotfixMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Hotfix record);

    int insertSelective(Hotfix record);

    int insertBatch(List<Hotfix> hotfixes);

    int deleteByMac(String mac);

    Hotfix selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hotfix record);

    int updateByPrimaryKey(Hotfix record);

    List<Hotfix> findAll(MyParam param);

    List<String> selectAbnormalHosts();

    int selectAbnormalCount();

}
