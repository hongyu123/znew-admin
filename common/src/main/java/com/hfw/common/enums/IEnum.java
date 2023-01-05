package com.hfw.common.enums;

/**
 * 枚举接口
 * 建议数据库表达状态的字段都映射成枚举,并实现此基类
 * @author zyh
 * @date 2022-04-06
 */
public interface IEnum {
    public int getCode();
    public String getDesc();
}
