package com.tpp.threat_perception_platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 主页
        registry.addViewController("/page/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        // 控制台
        registry.addViewController("/page/console").setViewName("console");
        // 登录页面
        registry.addViewController("/page/login").setViewName("login");
        // 用户相关页面
        registry.addViewController("/page/user/list").setViewName("user/list");
        registry.addViewController("/page/user/edit").setViewName("user/edit");
        registry.addViewController("/page/user/add").setViewName("user/add");
        registry.addViewController("/page/user/password").setViewName("user/password");
        // 角色相关页面
        registry.addViewController("/page/role/list").setViewName("role/list");
        registry.addViewController("/page/role/edit").setViewName("role/edit");
        registry.addViewController("/page/role/add").setViewName("role/add");
        // 主机列表
        registry.addViewController("/page/host/list").setViewName("host/list");
//        资产探测
        registry.addViewController("/page/host/assets").setViewName("host/assets");
        // 威胁检测
        registry.addViewController("/page/host/threats").setViewName("host/threats");
//        获取主机详细信息
        registry.addViewController("/page/host/detail").setViewName("host/detail");
//        资产
         // 账号
        registry.addViewController("/page/assets/account/list").setViewName("assets/account/list");
        registry.addViewController("/page/assets/account/detail").setViewName("assets/account/detail");
        // 服务
        registry.addViewController("/page/assets/service/list").setViewName("assets/service/list");
        registry.addViewController("/page/assets/service/detail").setViewName("assets/service/detail");
        // 进程
        registry.addViewController("/page/assets/process/list").setViewName("assets/process/list");
        registry.addViewController("/page/assets/process/detail").setViewName("assets/process/detail");
        // 应用
        registry.addViewController("/page/assets/app/list").setViewName("assets/app/list");
        registry.addViewController("/page/assets/app/detail").setViewName("assets/app/detail");
//        威胁
        // 补丁威胁发现结果
        registry.addViewController("/page/threats/hotfix/list").setViewName("threats/hotfix/list");
        // 漏洞威胁发现结果
        registry.addViewController("/page/threats/vulnerability/list").setViewName("threats/vulnerability/list");
        // 弱密码威胁发现结果
        registry.addViewController("/page/threats/weak_pwd/list").setViewName("threats/weak_pwd/list");
        // 应用风险发现结果
        registry.addViewController("/page/threats/app/list").setViewName("threats/app/list");

        // 漏洞库管理
        registry.addViewController("/page/vulnerability/list").setViewName("vulnerability/list");
        registry.addViewController("/page/vulnerability/edit").setViewName("vulnerability/edit");
        registry.addViewController("/page/vulnerability/add").setViewName("vulnerability/add");

        // 应用风险库管理
        registry.addViewController("/page/app_threat_db/list").setViewName("app_threat_db/list");
        registry.addViewController("/page/app_threat_db/edit").setViewName("app_threat_db/edit");
        registry.addViewController("/page/app_threat_db/add").setViewName("app_threat_db/add");

        // 日志
        registry.addViewController("/page/logs/list").setViewName("logs/list");
        registry.addViewController("/page/logs/list/account").setViewName("logs/account_list");
        registry.addViewController("/page/logs/list/login").setViewName("logs/login_list");


        // 基线任务
        registry.addViewController("/page/baseline/task/list").setViewName("baseline/task/list");
        registry.addViewController("/page/baseline/task/add").setViewName("baseline/task/add");
        registry.addViewController("/page/baseline/task/edit").setViewName("baseline/task/edit");
        // 基线检查结果
        registry.addViewController("/page/baseline/res/list").setViewName("baseline/res/list");
        registry.addViewController("/page/baseline/res/detail").setViewName("baseline/res/detail");

        // 操作日志记录
        registry.addViewController("/page/oper_log/list").setViewName("oper_log/list");
    }
}
