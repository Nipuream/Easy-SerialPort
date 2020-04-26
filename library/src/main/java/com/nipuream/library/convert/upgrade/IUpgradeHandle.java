package com.nipuream.library.convert.upgrade;


import com.nipuream.library.common.UpgradeInfo;

/**
 * Created by yanghui11 on 2020/4/9.
 */

public interface IUpgradeHandle {

    /**
     * 处理串口发来的特殊字符
     * @param data
     */
    void handle(byte[] data);

    /**
     * 是否在进行外设升级
     * @return
     */
    boolean isUpgrading();

    /**
     * 改变升级信息
     * @param info
     */
    void changeUpgradeInfo(UpgradeInfo info);

}
