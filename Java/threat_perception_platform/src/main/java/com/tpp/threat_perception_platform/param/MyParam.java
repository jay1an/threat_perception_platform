package com.tpp.threat_perception_platform.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用参数对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyParam {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 限制
     */
    private Integer limit;
    /**
     * 开始时间
     */
    private String start;
    /**
     * 结束时间
     */
    private String end;
    /**
     * 搜索关键字
     */
    private String keywords;
    /**
     * 数据ID
     */
    private Integer id;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 额外参数
     * (用于查询日志的事件id)
     */
    private Integer extra;

}
