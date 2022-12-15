package com.ruoyi.weibank.handler.base;

import java.time.LocalDateTime;

public interface ReportContext<T> {

    /**
     * 获取存储对象
     * @return
     */
    T getData();

    /**
     * 下次执行
     * @param time 执行时间
     * @param reason 原因
     */
    void doNextTime(LocalDateTime time, String reason);

    /**
     * 任务结束
     * @param reportFile 报表文件
     */
    void end(ReportFile reportFile);

    /**
     * 任务执行异常
     * @param msg
     */
    void fail(String msg);

    /**
     * 进度汇报 0-1
     * @param progress 进度
     */
    void progress(float progress);
}
