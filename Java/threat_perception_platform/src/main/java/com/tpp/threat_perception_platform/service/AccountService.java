package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Account;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface AccountService {

    void processAccounts(List<Account> accounts);

    ResponseResult<?> list(MyParam param);

    List<String> selectUsernamesByMac(String mac);
}
