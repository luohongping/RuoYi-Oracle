package com.ruoyi.weibank.handler.base;

import com.ruoyi.weibank.exception.ReportHandleException;

/**
 * @interfaceName ReportHandler
 * @description:
 * @author: hp.l
 * @create: 2022-12-13 16:10
 * @Version 1.0
 **/
public interface ReportHandler<T> {

	String type();

	void handle(T data, ReportContext<T> context) throws ReportHandleException;

}