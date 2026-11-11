package com.hfw.model.excel;

import com.hfw.model.enums.sys.BaseEnum;
import org.apache.fesod.sheet.converters.Converter;
import org.apache.fesod.sheet.enums.CellDataTypeEnum;
import org.apache.fesod.sheet.exception.ExcelDataConvertException;
import org.apache.fesod.sheet.metadata.GlobalConfiguration;
import org.apache.fesod.sheet.metadata.data.ReadCellData;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.apache.fesod.sheet.metadata.property.ExcelContentProperty;

import java.lang.reflect.Method;

/**
 * BaseEnum转换
 * @author farkle
 * @date 2023-03-16
 */
public class IEnumConverter implements Converter<BaseEnum> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return BaseEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BaseEnum convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellValue = cellData.getType()==CellDataTypeEnum.STRING ?cellData.getStringValue():cellData.getNumberValue().toString();
        Class<?> type = contentProperty.getField().getType();
        if(BaseEnum.class.isAssignableFrom(type)){
            Method method = type.getMethod("values");
            Object arr = method.invoke(null);
            if(arr.getClass().isArray()) {
                BaseEnum[] enums = (BaseEnum[]) arr;
                for(BaseEnum e : enums){
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
    public WriteCellData<?> convertToExcelData(BaseEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>( value==null ?"":value.getDesc() );
    }

}
