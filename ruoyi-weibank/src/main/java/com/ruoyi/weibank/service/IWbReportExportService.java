package com.ruoyi.weibank.service;

import java.time.LocalDateTime;
import java.util.List;
import com.ruoyi.weibank.domain.WbReportExport;
import com.ruoyi.weibank.handler.base.ReportFile;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 报导出记录Service接口
 * 
 * @author lhp
 * @date 2022-12-14
 */
public interface IWbReportExportService extends BeanPostProcessor
{
    /**
     * 查询报导出记录
     * 
     * @param expId 报导出记录主键
     * @return 报导出记录
     */
    public WbReportExport selectWbReportExportByExpId(String expId);

    /**
     * 查询报导出记录列表
     * 
     * @param wbReportExport 报导出记录
     * @return 报导出记录集合
     */
    public List<WbReportExport> selectWbReportExportList(WbReportExport wbReportExport);

    /**
     * 新增报导出记录
     * 
     * @param wbReportExport 报导出记录
     * @return 结果
     */
    public int insertWbReportExport(WbReportExport wbReportExport);

    /**
     * 修改报导出记录
     * 
     * @param wbReportExport 报导出记录
     * @return 结果
     */
    public int updateWbReportExport(WbReportExport wbReportExport);

    /**
     * 批量删除报导出记录
     * 
     * @param expIds 需要删除的报导出记录主键集合
     * @return 结果
     */
    public int deleteWbReportExportByExpIds(String expIds);

    /**
     * 删除报导出记录信息
     * 
     * @param expId 报导出记录主键
     * @return 结果
     */
    public int deleteWbReportExportByExpId(String expId);

    /**
     * @Description: 下次执行
     * @Author: hp.l
     * @Date: 2022/12/14 15:21
     * @param record
     * @param time
     * @param reason
     **/
	void doNextTime(WbReportExport record, LocalDateTime time, String reason);

	/**
	 * @Description: 任务结束
	 * @Author: hp.l
	 * @Date: 2022/12/14 15:21
	 * @param expId
	 * @param reportFile
	 **/
    void end(String expId, ReportFile reportFile);

    /**
     * @Description: 变更状态
     * @Author: hp.l
     * @Date: 2022/12/14 15:22
     * @param expId
     * @param status
     * @param time
     * @param reason
     **/
    void status(String expId, String status, LocalDateTime time, String reason);

    /**
     * @Description: 存储进度
     * @Author: hp.l
     * @Date: 2022/12/14 15:22
     * @param expId
     * @param progress
     **/
    void progress(String expId, float progress);
}
