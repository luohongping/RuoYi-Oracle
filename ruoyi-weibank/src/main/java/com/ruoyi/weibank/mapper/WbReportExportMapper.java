package com.ruoyi.weibank.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.ruoyi.weibank.domain.ReportExportFile;
import com.ruoyi.weibank.domain.WbReportExport;
import org.apache.ibatis.annotations.Param;

/**
 * 报导出记录Mapper接口
 * 
 * @author lhp
 * @date 2022-12-14
 */
public interface WbReportExportMapper 
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
     * 删除报导出记录
     * 
     * @param expId 报导出记录主键
     * @return 结果
     */
    public int deleteWbReportExportByExpId(String expId);

    /**
     * 批量删除报导出记录
     * 
     * @param expIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWbReportExportByExpIds(String[] expIds);

    WbReportExport getOne(@Param("types") Set<String> types);

    int start(@Param("expId") String expId);

    int clearFile(@Param("reportId") String reportId);

    int insertFile(@Param("file") ReportExportFile file);

    int end(@Param("expId") String expId, @Param("fileType") String fileType, @Param("length") int length, @Param("zip") String zip);

    int status(@Param("expId") String expId, @Param("status") String status, @Param("time") LocalDateTime time, @Param("reason") String reason);
}
