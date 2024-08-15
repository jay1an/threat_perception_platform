package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.mapper.AppThreatsDbMapper;
import com.tpp.threat_perception_platform.mapper.HostMapper;
import com.tpp.threat_perception_platform.mapper.WeakPwdDictMapper;
import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.ThreatsParam;
import com.tpp.threat_perception_platform.pojo.AppThreatsDb;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.Vulnerability;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HostServiceImpl implements HostService {

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private MQService rabbitMQService;

    @Autowired
    private VulnerabilityService vulnerabilityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServiceServiceImpl serviceService;

    @Autowired
    private AppThreatsDbService appThreatsDbService;

    @Autowired
    private WeakPwdDictMapper  weakPwdDictMapper;



    // save这个不在web端调用，在MQ consumer 处调用
    @Override
    public int save(Host host) {
        // 先查询主机是否在数据库中
        Host dbHost = hostMapper.selectByMac(host.getMac());
        if(dbHost!=null){
            // 主机已经在数据库，执行更新操作
            // 设置id
            host.setId(dbHost.getId());
            // 设置更新时间
            host.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return hostMapper.updateByPrimaryKeySelective(host);
        }
        // 主机第一次上线，则需要创建主机对应的队列,并绑定(这个队列在主机第一次上线之后就创建了，不会被销毁)
        String exchange = "agent_exchange";
        String mac = host.getMac().replace(":","");
        String queueName = "agent_"+mac+"_queue";
        String routingKey = mac;
        rabbitMQService.createAgentQueue(exchange,queueName,routingKey);
        // 创建队列后继续插入逻辑
        // 如果不在数据库中
        // 设置时间
        host.setCreateTime(new Timestamp(System.currentTimeMillis()));
        host.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        // 设置状态为上线
        host.setStatus(1);
        // 设置日志同步 - 关
        host.setSyncLogs(0);
        // 插入
        return hostMapper.insert(host);
    }

    @Override
    public ResponseResult<?> list(MyParam param) {
        // 分页
        PageHelper.startPage(param.getPage(),param.getLimit());
        // 执行分页查询
        List<Host> data = hostMapper.findAll(param);
        // 获取信息
        PageInfo<Host> pageInfo = new PageInfo<>(data);
        // 封装返回结果
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult<?> list() {
        return new ResponseResult<>(0,hostMapper.findAllOnlyWithMacAndHostname());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<?> delete(Integer[] ids) {
        hostMapper.deletebyIds(ids);
        return new ResponseResult<>(0,"删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusOnline(String mac) {
        Host host = new Host();
        host.setMac(mac);
        host.setStatus(1);
        host.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        hostMapper.updateByMacSelective(host);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusOffline(String mac) {
        Host host = new Host();
        host.setMac(mac);
        host.setStatus(0);
        host.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        hostMapper.updateByMacSelective(host);
    }

    @Override
    public ResponseResult<?> setSyncLogsOn(String mac) {
        try {
            Host host = new Host();
            host.setMac(mac);
            host.setSyncLogs(1);
            hostMapper.updateByMacSelective(host);
            return new ResponseResult<>(0,"同步日志开启成功");
        } catch (Exception e) {
            return new ResponseResult<>(-1,"同步日志开启失败:"+e.getMessage());
        }
    }

    @Override
    public ResponseResult<?> setSyncLogsOff(String mac) {
        try {
            Host host = new Host();
            host.setMac(mac);
            host.setSyncLogs(0);
            hostMapper.updateByMacSelective(host);
            return new ResponseResult<>(0,"同步日志关闭成功");
        } catch (Exception e) {
            return new ResponseResult<>(-1,"同步日志关闭失败:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Host> findOnlineHosts() {
        MyParam param = new MyParam();
        param.setStatus(1);
        return hostMapper.findAll(param);
    }

    @Override
    public List<Host> findOnlineHostsAndSyncLogsOn() {
        MyParam param = new MyParam();
        param.setExtra(1);
        param.setStatus(1);
        return hostMapper.findAll(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<?> assetsDetection(AssetsParam param) {
        Host db_host = hostMapper.selectByMac(param.getMac());
        if(db_host.getStatus()==0){
            return new ResponseResult<>(-1,"主机已下线");
        }else if(System.currentTimeMillis()-db_host.getUpdateTime().getTime()>4000){
            updateStatusOffline(param.getMac());
            return new ResponseResult<>(-1,"主机已下线");
        }else{
            // 主机在线上的情况
            // 将param转换为json发送到消息队列
            String data = JSON.toJSONString(param);
            String exchange = "agent_exchange";
            String routingKey = param.getMac().replace(":","");
            rabbitMQService.sendMessage(exchange,routingKey,data);
            return new ResponseResult<>(0,"资产探测命令已发送！");
        }
    }

    @Override
    public ResponseResult<?> threatsDetection(ThreatsParam param) {
        Host db_host = hostMapper.selectByMac(param.getMac());
        if(db_host.getStatus()==0){
            return new ResponseResult<>(-1,"主机已下线");
        }else if(System.currentTimeMillis()-db_host.getUpdateTime().getTime()>4000){
            updateStatusOffline(param.getMac());
            return new ResponseResult<>(-1,"主机已下线");
        }else{
            // 主机在线上的情况
            if(param.getVulnerability()==1){
                // 要进行漏洞检测
                // 获取平台漏洞数据库数据
                List<Vulnerability> vulnerabilities = vulnerabilityService.list2();
                param.setVulnerabilities(vulnerabilities);
            }

            if(param.getWeakPwd()==1){
                // 如果要进行弱密码检测
                // 先获取弱密码数据和 系统的用户名
                List<String> weakPwds = weakPwdDictMapper.selectValues();
                List<String> usernames = accountService.selectUsernamesByMac(param.getMac());
                // 如果有mysql服务: 获取mysql服务端口 + 弱密码数据
                HashMap<String,List<String>> map = new HashMap<>();

                String[]  servicesKeywords = {"mysql"}; // 后续可加ssh redis等
                for(String serviceKeyword:servicesKeywords){
                    List<com.tpp.threat_perception_platform.pojo.Service> services= serviceService.findHostServiceByKeywords(serviceKeyword,param.getMac());
                    if(!services.isEmpty()){
                        map.put(serviceKeyword,new ArrayList<>());
                    }
                    for(com.tpp.threat_perception_platform.pojo.Service service:services){
                        map.get(serviceKeyword).add(String.valueOf(service.getPort()));
                    }
                }
                // 给param赋值
                param.setWeakPwds(weakPwds);  // 弱密码
                param.setUsernames(usernames); // 系统账号
                param.setWeakPwdServiceMap(map);  // 要探测的服务数据
            }

            if(param.getApp()==1){
                // 如果要对应用风险进行检测
                // 先获取要检测的可能的应用，这需要在service表中查询
                String[] servicesKeywords = {"tomcat"}; // 后续可加ftp、nginx等
                HashMap<String,List<String>> appThreatsServiceMap = new HashMap<>();
                for(String serviceKeyword:servicesKeywords){
                    List<com.tpp.threat_perception_platform.pojo.Service> services= serviceService.findHostServiceByKeywords(serviceKeyword,param.getMac());
                    if(!services.isEmpty()){
                        appThreatsServiceMap.put(serviceKeyword,new ArrayList<>());
                    }
                    for(com.tpp.threat_perception_platform.pojo.Service service:services){
                        appThreatsServiceMap.get(serviceKeyword).add(String.valueOf(service.getPort()));
                    }
                }
                // 获取对应的数据
                HashMap<String,List<AppThreatsDb>> appThreatsDbMap = new HashMap<>();
                for (String keyword : appThreatsServiceMap.keySet()) {
                    List<AppThreatsDb> appThreatsDbs = appThreatsDbService.findByKeywords(keyword);
                    appThreatsDbMap.computeIfAbsent(keyword, k -> new ArrayList<>()).addAll(appThreatsDbs);
                }
                // 给param赋值
                param.setAppThreatsDbMap(appThreatsDbMap);
                param.setAppThreatsServiceMap(appThreatsServiceMap);
            }
            // 将param转换为json发送到消息队列
            String data = JSON.toJSONString(param);
            String exchange = "agent_exchange";
            String routingKey = param.getMac().replace(":","");
            rabbitMQService.sendMessage(exchange,routingKey,data);
            return new ResponseResult<>(0,"威胁检测命令已发送！");
        }
    }

    @Override
    public ResponseResult<?> hostDetails(String mac) {
        Host host = hostMapper.selectByMac(mac);
        if(host!=null){
            return new ResponseResult<>(0,host);
        }
        return new ResponseResult<>(-1,"主机不存在");
    }
}
