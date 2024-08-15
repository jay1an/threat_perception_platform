package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.pojo.WeakPwdDict;

import java.util.List;

/**
* @author 23351
* @description 针对表【weak_pwd_dict】的数据库操作Mapper
* @createDate 2024-06-18 15:37:04
* @Entity com.tpp.threat_perception_platform.pojo.WeakPwdDict
*/
public interface WeakPwdDictMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WeakPwdDict record);

    int insertSelective(WeakPwdDict record);

    WeakPwdDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeakPwdDict record);

    int updateByPrimaryKey(WeakPwdDict record);

    List<String> selectValues();
}
