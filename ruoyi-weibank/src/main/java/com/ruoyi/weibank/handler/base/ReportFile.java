package com.ruoyi.weibank.handler.base;

import java.io.Closeable;

public interface ReportFile extends Closeable {

    /**
     * 文件类型
     * @return
     */
    String type();

    /**
     * 是否压缩
     * @return
     */
    boolean isZip();

    byte[] getBytes();
}
