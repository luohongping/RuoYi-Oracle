package com.ruoyi.weibank.handler;


import com.ruoyi.weibank.domain.WbReportExport;
import com.ruoyi.weibank.handler.base.ReportContext;
import com.ruoyi.weibank.handler.base.ReportFile;
import com.ruoyi.weibank.service.IWbReportExportService;
import com.ruoyi.weibank.service.impl.WbReportExportServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public class CrmReportContext implements ReportContext<WbReportExport> {


    private final IWbReportExportService wbReportExportService;

    private final WbReportExport record;

    public CrmReportContext(IWbReportExportService wbReportExportService, WbReportExport record) {
        this.wbReportExportService = wbReportExportService;
        this.record = record;
    }

    @Override
    public WbReportExport getData() {
        return this.record;
    }

    @Override
    public void doNextTime(LocalDateTime time, String reason) {
        wbReportExportService.doNextTime(this.record, time, reason);
    }

    @Override
    public void end(ReportFile reportFile) {
        if(reportFile == null){
            fail("无文件生成");
            return;
        }
        wbReportExportService.end(record.getExpId(), reportFile);
    }

    @Override
    public void fail(String msg) {
        wbReportExportService.status(record.getExpId(), "E", null, msg);
    }

    @Override
    public void progress(float progress) {
        log.info("报表[{}]生成进度：{}", record.getExpId(), progress);
        wbReportExportService.progress(record.getExpId(), progress);
    }
}
