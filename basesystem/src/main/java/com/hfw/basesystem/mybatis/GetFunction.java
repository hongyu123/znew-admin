package com.hfw.basesystem.mybatis;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 为Lambada表达式获取方法的对应信息
 * @author zyh
 * @date 2022-06-10
 */
@FunctionalInterface
public interface GetFunction<T,R> extends Function<T,R>, Serializable {

    default SerializedLambda getSerializedLambda() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(this);
        return serializedLambda;
    }
    default String getMethodName() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SerializedLambda serializedLambda = getSerializedLambda();
        return serializedLambda.getImplMethodName();
    }
    default Class getClazz() throws Exception {
        SerializedLambda serializedLambda = getSerializedLambda();
        String className = serializedLambda.getImplClass().replace('/', '.');
        return Class.forName(className);
    }

}
