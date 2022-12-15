package com.ruoyi.weibank.vo;

import lombok.Data;

@Data
public class ReportCreateVo {

    private String type;

    /**
     * 所属应用 sms,cc,workflow,mkt
     */
    private String app;

    /**
     * 文件业务唯一ID
     */
    private String uniqueId;

    /**
     * 业务定义，不带文件后缀，最大200
     */
    private String name;

    /**
     * 报表导出条件
     */
    private String params;
}
