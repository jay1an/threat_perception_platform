package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.AccountMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Account;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processAccounts(List<Account> accounts) {
        String mac = accounts.get(0).getMac();
        // 先删除旧的账号
        accountMapper.deleteByMac(mac);
        // 插入新的账号
        accountMapper.insertBatch(accounts);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        // 设置分页
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 执行查询
        List<Account> accounts = accountMapper.findAll(param);
        // 获取分页信息
        PageInfo<Account> pageInfo = new PageInfo<>(accounts);
        return new ResponseResult<>(pageInfo.getTotal(), accounts);
    }

    @Override
    public List<String> selectUsernamesByMac(String mac) {
        return accountMapper.selectUsernamesByMac(mac);
    }

}
