package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 23351
* @description 针对表【role】的数据库操作Mapper
* @createDate 2024-06-05 11:21:33
* @Entity com.tpp.threat_perception_platform.pojo.Role
*/
public interface RoleMapper {

    int deletebyIds(@Param("ids") Integer[] id);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> findAll(MyParam param);

    Role selectByPrimaryKey(Long id);

    Role selectByRoleName(String roleName);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}
