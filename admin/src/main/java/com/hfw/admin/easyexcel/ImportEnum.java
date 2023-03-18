package com.hfw.admin.easyexcel;

/**
 * @author farkle
 * @date 2023-03-17
 */
public enum ImportEnum {
    /**
     * 自动
     * 自动判断数据量过多时采用过滤失败数据的方案
     */
    auto,
    /**
     * 全部导入
     * 所有一起成功或失败
     */
    all,
    /**
     * 过滤失败数据
     * 允许校验通过的数据导入成功,校验不通过的数据导入失败
     */
    filter_fail
}
