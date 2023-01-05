package com.hfw.basesystem.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Collection;
import java.util.Map;

/**
 * 属性转换工厂
 */
public class PropertyObjectWrapperFactory implements ObjectWrapperFactory {
  @Override
  public boolean hasWrapperFor(Object object) {
    //忽略Map和集合
    if(object instanceof Map || object instanceof Collection){
      return false;
    }
    return true;
  }

  @Override
  public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
    return new PropertyBeanWrapper(metaObject,object);
  }

}
