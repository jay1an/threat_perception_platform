package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.RoleMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseResult<?> list(MyParam param) {
        // 设置分页
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 执行查询
        List<Role> data = roleMapper.findAll(param);
        // 获取分页信息
        PageInfo<Role> pageInfo = new PageInfo<>(data);
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult<?> list() {
        return new ResponseResult<>(0, roleMapper.findAll(null));
    }

    @Override
    public ResponseResult<?> save(Role role) {
        // 查询是否已有同名role
        Role role1 = roleMapper.selectByRoleName(role.getRoleName());
        // 若有同名角色直接退出。
        if (role1 != null) return new ResponseResult<>(1, "已有同名角色");
        else {
            roleMapper.insertSelective(role);
            return new ResponseResult<>(0, "操作成功");
        }
    }

    @Override
    public ResponseResult<?> delete(Integer[] ids) {
        roleMapper.deletebyIds(ids);
        return new ResponseResult<>(0, "操作成功");
    }

    @Override
    public ResponseResult<?> edit(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
        return new ResponseResult<>(0, "操作成功");
    }
}
