package com.ruoyi.weibank.handler.impl;

import com.ruoyi.weibank.domain.ReportTypeEnum;
import com.ruoyi.weibank.domain.WbReportExport;
import com.ruoyi.weibank.exception.ReportHandleException;
import com.ruoyi.weibank.handler.CrmReportHandler;
import com.ruoyi.weibank.handler.base.ReportContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName DemoReportHandler
 * @description:
 * @author: hp.l
 * @create: 2022-12-14 16:15
 * @Version 1.0
 **/
@Slf4j
@Component
public class DemoReportHandler  implements CrmReportHandler {

	@Override
	public String type() {
		return ReportTypeEnum.DemoRepot.name();
	}

	@Override
	public void handle(WbReportExport data, ReportContext<WbReportExport> context) throws ReportHandleException {
		log.info("处理……DemoReportHandler");
	}
}