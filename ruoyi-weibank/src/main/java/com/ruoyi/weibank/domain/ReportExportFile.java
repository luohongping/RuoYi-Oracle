package com.ruoyi.weibank.domain;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName ReportExportFile
 * @description: 报表导出文件实体类
 * @author: hp.l
 * @create: 2022-12-14 15:31
 * @Version 1.0
 **/
@Data
public class ReportExportFile {

	//表id
	private String id;

	//reportExport表id
	private String  reportId;

	//序号
	private Integer seq;

	//创建时间
	private Date createTime;

	//大小
	private Integer fileSize;

	/**
	 * 查询多条时，请单独获取该数据，请勿批量插入
	 */
	private byte[] fileData;

}