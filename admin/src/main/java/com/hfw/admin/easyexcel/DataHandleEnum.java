package com.hfw.admin.easyexcel;

/**
 * @author farkle
 * @date 2023-03-17
 */
public enum DataHandleEnum {
    /**
     * 新增导入
     * 所有数据导入都是一条新的数据
     */
    insert,
    /**
     * 导入覆盖
     * 重复数据直接覆盖
     */
    cover,
    /**
     * 导入更新
     * 重复数据更新信息
     */
    update
}
