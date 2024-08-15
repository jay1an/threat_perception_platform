package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.pojo.WinCveDb;

import java.util.List;

/**
* @author 23351
* @description 针对表【win_cve_db(Windows漏洞与KB补丁关系库)】的数据库操作Mapper
* @createDate 2024-06-14 16:02:02
* @Entity com.tpp.threat_perception_platform.pojo.WinCveDb
*/
public interface WinCveDbMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WinCveDb record);

    int insertSelective(WinCveDb record);

    WinCveDb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WinCveDb record);

    int updateByPrimaryKey(WinCveDb record);

    List<String> selectCveByKb(String kb);

}
