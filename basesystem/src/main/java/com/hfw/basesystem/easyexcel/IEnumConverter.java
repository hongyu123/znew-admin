package com.hfw.basesystem.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.hfw.common.enums.IEnum;

import java.lang.reflect.Method;

/**
 * IEnum转换
 * @author farkle
 * @date 2023-03-16
 */
public class IEnumConverter implements Converter<IEnum> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return IEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public IEnum convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellValue = cellData.getType()==CellDataTypeEnum.STRING ?cellData.getStringValue():cellData.getNumberValue().toString();
        Class<?> type = contentProperty.getField().getType();
        if(IEnum.class.isAssignableFrom(type)){
            Method method = type.getMethod("values");
            Object arr = method.invoke(null);
            if(arr.getClass().isArray()) {
                IEnum[] enums = (IEnum[]) arr;
                for(IEnum e : enums){
                    if(e.getDesc().equals(cellValue)){
                        return e;
                    }
                }
            }
            throw new ExcelDataConvertException(cellData.getRowIndex(), cellData.getColumnIndex(), cellData, contentProperty,cellValue+"转换错误");
        }
        return null;
    }

    @Override
    public WriteCellData<?> convertToExcelData(IEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>( value==null ?"":value.getDesc() );
    }

}
