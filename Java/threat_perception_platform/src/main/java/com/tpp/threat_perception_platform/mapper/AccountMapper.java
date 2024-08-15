package com.tpp.threat_perception_platform.mapper;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Account;

import java.util.List;

/**
* @author 23351
* @description 针对表【account】的数据库操作Mapper
* @createDate 2024-06-12 12:35:15
* @Entity com.tpp.threat_perception_platform.pojo.Account
*/
public interface AccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> selectByMac(String mac);

    Account selectByMacAndName(String mac, String name);

    Account selectByMacAndSid(String mac, String sid);

    int deleteByMac(String mac);

    int insertBatch(List<Account> accounts);

    List<Account> findAll(MyParam param);

    List<String> selectUsernamesByMac(String mac);

    int selectCount();
}
