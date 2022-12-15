package com.ruoyi.weibank.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.spring.SpringRedisLock;
import com.ruoyi.weibank.domain.ReportExportFile;
import com.ruoyi.weibank.exception.ReportHandleException;
import com.ruoyi.weibank.handler.CrmReportContext;
import com.ruoyi.weibank.handler.CrmReportHandler;
import com.ruoyi.weibank.handler.base.ReportContext;
import com.ruoyi.weibank.handler.base.ReportFile;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.weibank.mapper.WbReportExportMapper;
import com.ruoyi.weibank.domain.WbReportExport;
import com.ruoyi.weibank.service.IWbReportExportService;
import com.ruoyi.common.core.text.Convert;

/**
 * 报导出记录Service业务层处理
 * 
 * @author lhp
 * @date 2022-12-14
 */
@Slf4j
@Service
public class WbReportExportServiceImpl implements IWbReportExportService
{
    @Autowired
    private WbReportExportMapper wbReportExportMapper;


    private final Map<String, CrmReportHandler> handlerMap;
    private final SpringRedisLock springRedisLock;
    private final StringRedisTemplate stringRedisTemplate;
    private final Timer timer;

    // 0.5M
    private static final int blockSize = 512*1024;

    public WbReportExportServiceImpl(StringRedisTemplate stringRedisTemplate){
        handlerMap = new HashMap<>();
        this.stringRedisTemplate = stringRedisTemplate;
        this.springRedisLock = new SpringRedisLock(this.stringRedisTemplate);
        this.timer = new Timer("REPORT:EXPORT:TIMER", true);
    }


    /**
     * @Description: 实例化、依赖注入、初始化完毕时执行
     * @Author: hp.l
     * @Date: 2022/12/14 14:41
     * @param bean
     * @param beanName
     * @return java.lang.Object
     **/
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(CrmReportHandler.class.isAssignableFrom(bean.getClass())){
            //报表导出处理类
            CrmReportHandler handler = (CrmReportHandler) bean;
            log.info("添加报表【{}】导出处理方法：{}<{}>",handler.type(),beanName,bean.getClass().getName());
            handlerMap.put(handler.type(),handler);
        }
        return bean;
    }

    /**
     * 查询报导出记录
     * 
     * @param expId 报导出记录主键
     * @return 报导出记录
     */
    @Override
    public WbReportExport selectWbReportExportByExpId(String expId)
    {
        return wbReportExportMapper.selectWbReportExportByExpId(expId);
    }

    /**
     * 查询报导出记录列表
     * 
     * @param wbReportExport 报导出记录
     * @return 报导出记录
     */
    @Override
    public List<WbReportExport> selectWbReportExportList(WbReportExport wbReportExport)
    {
        return wbReportExportMapper.selectWbReportExportList(wbReportExport);
    }

    /**
     * 新增报导出记录
     * 
     * @param wbReportExport 报导出记录
     * @return 结果
     */
    @Override
    public int insertWbReportExport(WbReportExport wbReportExport)
    {
        wbReportExport.setCreateTime(DateUtils.getNowDate());
        wbReportExport.setExpId(UUID.randomUUID().toString());
        return wbReportExportMapper.insertWbReportExport(wbReportExport);
    }

    /**
     * 修改报导出记录
     * 
     * @param wbReportExport 报导出记录
     * @return 结果
     */
    @Override
    public int updateWbReportExport(WbReportExport wbReportExport)
    {
        return wbReportExportMapper.updateWbReportExport(wbReportExport);
    }

    /**
     * 批量删除报导出记录
     * 
     * @param expIds 需要删除的报导出记录主键
     * @return 结果
     */
    @Override
    public int deleteWbReportExportByExpIds(String expIds)
    {
        return wbReportExportMapper.deleteWbReportExportByExpIds(Convert.toStrArray(expIds));
    }

    /**
     * 删除报导出记录信息
     * 
     * @param expId 报导出记录主键
     * @return 结果
     */
    @Override
    public int deleteWbReportExportByExpId(String expId)
    {
        return wbReportExportMapper.deleteWbReportExportByExpId(expId);
    }

    @Override
    public void doNextTime(WbReportExport record, LocalDateTime time, String reason) {
        if (record.getExecCount() != null && record.getExecCount() >2){
            //第三次执行了，异常结束
            log.info("报表[{}]异常超过3次！", record.getExpId());
            status(record.getExpId(), "E", null, reason);
        }else {
            status(record.getExpId(), "N", time, reason);
        }

    }

    /**
     * @Description: 处理结束
     * @Author: hp.l
     * @Date: 2022/12/14 15:25
     * @param expId
     * @param reportFile
     **/
    @Override
    public void end(String expId, ReportFile reportFile) {
        log.info("报表[{}]导出结束", expId);
        wbReportExportMapper.clearFile(expId);
        // 生成文件
        byte[] data = reportFile.getBytes();
        int count = data.length / blockSize + 1;
        for(int i = 0 ; i < count; ++i){
            int length = Math.min(data.length - (i * blockSize), blockSize);
            if(length < 1){
                break;
            }
            ReportExportFile file = new ReportExportFile();
            file.setId(UUID.randomUUID().toString());
            file.setCreateTime(new Date());
            file.setSeq(i);
            file.setReportId(expId);
            byte[] rdata = new byte[length];
            System.arraycopy(data, i * blockSize, rdata, 0, length);
            file.setFileData(rdata);
            file.setFileSize(rdata.length);
            log.info("插入报表[{}]文件片段：{}-{}byte", expId, i, length);
            wbReportExportMapper.insertFile(file);
        }
        wbReportExportMapper.end(expId,  reportFile.type(), data.length, reportFile.isZip() ? "Y" : "N");
    }


    /**
     * @Description:  改变状态
     * @Author: hp.l
     * @Date: 2022/12/14 15:40
     * @param expId
     * @param status
     * @param time
     * @param reason
     **/
    @Override
    public void status(String expId, String status, LocalDateTime time, String reason){
        log.info("报表[{}]状态变更: {}", expId, status);
        wbReportExportMapper.status(expId, status, time, StringUtils.substring(reason,0,250));
    }



    @Override
    public void progress(String expId, float progress) {
        if(progress > 1){
            progress = 1;
        } else if (progress < 0){
            progress = 0;
        }
        stringRedisTemplate.opsForValue().set("REPORT:EXPORT-P:" + expId, progress + "", 300, TimeUnit.SECONDS);
    }


    public void handle(){
        if(handlerMap.isEmpty()){
            return;
        }
        int i = 0;
        while(i++ < 5){
            // 获取记录
            WbReportExport wbReportExport = wbReportExportMapper.getOne(handlerMap.keySet());
            if(wbReportExport == null){
                continue;
            }
            String key = "REPORT:EXPORT:" + wbReportExport.getExpId();
            String value = UUID.randomUUID().toString();
            TimerTask task = null;
            try{
                if(!springRedisLock.lock(key, value, 300)){
                    continue;
                }
                // 开始执行（开启定时器刷新锁）
                log.info("生成报表：{}", wbReportExport.getExpId());
                wbReportExportMapper.start(wbReportExport.getExpId());
                task = new TimerTask() {
                    @Override
                    public void run() {
                        springRedisLock.lock(key, value, 300);
                    }
                };
                timer.schedule(task, new Date(), 180_000);
                execHandler(wbReportExport, new CrmReportContext(this, wbReportExport));
                log.info("生成报表结束：{}", wbReportExport.getExpId());
                break;
            } finally {
                if(task != null){
                    task.cancel();
                }
                springRedisLock.releaseLock(key, value);
            }
        }

    }

    private void execHandler(WbReportExport record, ReportContext<WbReportExport> context){
        if(!handlerMap.containsKey(record.getReportType())){
            context.fail("不存在的报表类型！");
            return;
        }
        try {
            handlerMap.get(record.getReportType()).handle(record, context);
        } catch (ReportHandleException e) {
            log.error("处理报表{}异常", record.getExpId(), e);
            context.doNextTime(LocalDateTime.now().plusMinutes(5), StringUtils.substring(e.getMessage(),0,200));
        }
    }

}
