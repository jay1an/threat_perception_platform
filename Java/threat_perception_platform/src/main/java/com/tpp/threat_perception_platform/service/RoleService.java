package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface RoleService {
    ResponseResult<?> list(MyParam param);

    ResponseResult<?> list();

    ResponseResult<?> save(Role role);

    ResponseResult<?> delete(Integer[] id);

    ResponseResult<?> edit(Role role);

}
