package com.ruoyi.weibank.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报导出记录对象 wb_report_export
 *
 * @author lhp
 * @date 2022-12-14
 */
public class WbReportExport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String expId;

    /** 报表类型 */
    @Excel(name = "报表类型")
    private String reportType;

    /** 报表名称 */
    @Excel(name = "报表名称")
    private String reportName;

    /** 报表所属应用 */
    @Excel(name = "报表所属应用")
    private String app;

    /** 业务ID */
    @Excel(name = "业务ID")
    private String uniqueId;

    /** 报表导出参数 */
    @Excel(name = "报表导出参数")
    private String reportParams;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 是否压缩Y-压缩N-未压缩 */
    @Excel(name = "是否压缩Y-压缩N-未压缩")
    private String zip;

    /** 当前状态 */
    @Excel(name = "当前状态")
    private String status;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private String createUser;

    /** 创建用户名 */
    @Excel(name = "创建用户名")
    private String createUserName;

    /** 执行异常原因 */
    @Excel(name = "执行异常原因")
    private String reason;

    /** 执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextExecTime;

    /** 执行结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "执行结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 执行次数 */
    @Excel(name = "执行次数")
    private Long execCount;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private Long reportSize;

    public void setExpId(String expId)
    {
        this.expId = expId;
    }

    public String getExpId()
    {
        return expId;
    }
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public String getReportType()
    {
        return reportType;
    }
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getReportName()
    {
        return reportName;
    }
    public void setApp(String app)
    {
        this.app = app;
    }

    public String getApp()
    {
        return app;
    }
    public void setUniqueId(String uniqueId)
    {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId()
    {
        return uniqueId;
    }
    public void setReportParams(String reportParams)
    {
        this.reportParams = reportParams;
    }

    public String getReportParams()
    {
        return reportParams;
    }
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileType()
    {
        return fileType;
    }
    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getZip()
    {
        return zip;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }
    public void setCreateUserName(String createUserName)
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName()
    {
        return createUserName;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    public void setNextExecTime(Date nextExecTime)
    {
        this.nextExecTime = nextExecTime;
    }

    public Date getNextExecTime()
    {
        return nextExecTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setExecCount(Long execCount)
    {
        this.execCount = execCount;
    }

    public Long getExecCount()
    {
        return execCount;
    }
    public void setReportSize(Long reportSize)
    {
        this.reportSize = reportSize;
    }

    public Long getReportSize()
    {
        return reportSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("expId", getExpId())
                .append("reportType", getReportType())
                .append("reportName", getReportName())
                .append("app", getApp())
                .append("uniqueId", getUniqueId())
                .append("reportParams", getReportParams())
                .append("fileType", getFileType())
                .append("zip", getZip())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createUser", getCreateUser())
                .append("createUserName", getCreateUserName())
                .append("reason", getReason())
                .append("nextExecTime", getNextExecTime())
                .append("endTime", getEndTime())
                .append("execCount", getExecCount())
                .append("reportSize", getReportSize())
                .toString();
    }
}
