package com.hfw.basesystem.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;

/**
 * 属性获取Wrapper
 * 解决问题:
 *  1.下换线属性转驼峰后获取不到原样获取
 *  2.不存在的属性返回null
 * @author zyh
 * @date 2022-12-06
 */
public class PropertyBeanWrapper extends BeanWrapper {

    public PropertyBeanWrapper(MetaObject metaObject, Object object) {
        super(metaObject, object);
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        String property = super.findProperty(name, useCamelCaseMapping);
        if(property==null){
            property = super.findProperty(name, false);
        }
        return property;
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        if(!super.hasGetter(prop.getName())){
            return null;
        }
        return super.get(prop);
    }

}
