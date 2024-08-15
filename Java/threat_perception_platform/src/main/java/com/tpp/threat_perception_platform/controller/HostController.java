package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.annotation.MyLog;
import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.ThreatsParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/host")
public class HostController {

    @Autowired
    private HostService hostService;


    @RequestMapping("/list")
    public ResponseResult<?> list(MyParam param) {
        return hostService.list(param);
    }

    /**
     * 这个/list/2接口返回只带有mac和host那么的Host
     * 用于前端下拉框的数据
     * @return
     */
    @PostMapping("/list/2")
    public ResponseResult<?> list() {
        return hostService.list();
    }

    @PostMapping("/delete")
    @MyLog(title = "主机模块", content = "删除主机")
    public ResponseResult<?> delete(@RequestParam("ids[]") Integer[] ids){
        return hostService.delete(ids);
    }

    @PostMapping("/setSyncLogsOn")
    @MyLog(title = "主机模块", content = "设置主机开启同步日志")
    public ResponseResult<?> setSyncLogsOn(@RequestBody Map<String, String> request) {
        String mac = request.get("mac");
        if (mac == null) {
            return new ResponseResult<>(-1,"issing required parameter 'mac'");
        }
        return hostService.setSyncLogsOn(mac);
    }

    @PostMapping("/setSyncLogsOff")
    @MyLog(title = "主机模块", content = "设置主机关闭同步日志")
    public ResponseResult<?> setSyncLogsOff(@RequestBody Map<String, String> request) {
        String mac = request.get("mac");
        if (mac == null) {
            return new ResponseResult<>(-1,"issing required parameter 'mac'");
        }
        return hostService.setSyncLogsOff(mac);
    }

    @PostMapping("/assets/detection")
    @MyLog(title = "主机模块", content = "发送资产探测消息")
    public ResponseResult<?> assetsDetection(@RequestBody AssetsParam param){
        return hostService.assetsDetection(param);
    }

    @PostMapping("/threats/discovery")
    @MyLog(title = "主机模块", content = "发送风险检测消息")
    public ResponseResult<?> threatsDetection(@RequestBody ThreatsParam param){
        return hostService.threatsDetection(param);
    }

    @PostMapping("/details")
    public ResponseResult<?> hostDetails(@RequestParam("mac") String mac){
        return hostService.hostDetails(mac);
    }


}
