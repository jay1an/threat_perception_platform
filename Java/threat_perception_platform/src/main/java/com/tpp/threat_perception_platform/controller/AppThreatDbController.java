package com.tpp.threat_perception_platform.controller;


import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppThreatsDb;
import com.tpp.threat_perception_platform.pojo.Vulnerability;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AppThreatsDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app_threat_db")
public class AppThreatDbController {

    @Autowired
    private AppThreatsDbService appThreatsDbService;

    @PostMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return appThreatsDbService.list(param);
    }

    @PostMapping("/save")
    @MyLog(title = "应用风险库模块", content = "添加应用风险")
    public ResponseResult<?> save(@RequestBody AppThreatsDb appThreatsDb) {
        return appThreatsDbService.save(appThreatsDb);
    }

    @PostMapping("/delete")
    @MyLog(title = "应用风险库模块", content = "删除应用风险")
    public ResponseResult<?> delete(@RequestParam("ids[]")  Integer[] ids) {
        return appThreatsDbService.delete(ids);
    }

    @PostMapping("/edit")
    @MyLog(title = "应用风险库模块", content = "修改应用风险")
    public ResponseResult<?> edit(@RequestBody AppThreatsDb appThreatsDb) {
        return appThreatsDbService.edit(appThreatsDb);
    }


}
