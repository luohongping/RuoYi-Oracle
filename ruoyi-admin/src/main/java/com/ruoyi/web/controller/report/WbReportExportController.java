package com.ruoyi.web.controller.report;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.weibank.vo.ReportCreateVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.weibank.domain.WbReportExport;
import com.ruoyi.weibank.service.IWbReportExportService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报导出记录Controller
 * 
 * @author lhp
 * @date 2022-12-14
 */
@Controller
@RequestMapping("/report/export")
public class WbReportExportController extends BaseController
{
    private String prefix = "report/export";

    @Autowired
    private IWbReportExportService wbReportExportService;

    @RequiresPermissions("report:export:view")
    @GetMapping()
    public String export()
    {
        return prefix + "/list";
    }

    /**
     * 查询报导出记录列表
     */
    @RequiresPermissions("report:export:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WbReportExport wbReportExport)
    {
        startPage();
        List<WbReportExport> list = wbReportExportService.selectWbReportExportList(wbReportExport);
        return getDataTable(list);
    }


    /**
     * 删除报导出记录
     */
    @RequiresPermissions("report:export:remove")
    @Log(title = "报导出记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(wbReportExportService.deleteWbReportExportByExpIds(ids));
    }


    public AjaxResult create(ReportCreateVo reportCreateVo){
        SysUser sysUser = getSysUser();
        //wbReportExportService.createReport(reportCreateVo,sysUser.getLoginName(),sysUser.getUserName());
        return null;
    }

}
