package com.hfw.admin.easyexcel;

import com.alibaba.excel.enums.CellDataTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * easyexcel错误数据
 * @author farkle
 * @date 2023-03-08
 */
@Data
public class ErrorData {
    //数据列表索引
    private Integer index;
    /** 字段名 */
    private String fieldName;

    /** 错误信息 */
    private String error;

    /** 原始数据 */
    private CellDataTypeEnum cellDataType;
    private BigDecimal numberValue;
    private String stringValue;
    private Boolean booleanValue;

}
