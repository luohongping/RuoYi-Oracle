package com.ruoyi.weibank.handler;


import com.ruoyi.weibank.domain.WbReportExport;
import com.ruoyi.weibank.handler.base.ReportHandler;

public interface CrmReportHandler extends ReportHandler<WbReportExport> {

    // 报表导出，每次循环的记录数
    int EXPORT_PAGE_SIZE = 200;

    // 导出报表的excel最大行数
    int EXPORT_MAX_ROWS = 10000;

}
