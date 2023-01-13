package com.hfw.basesystem.support.validation;

import javax.validation.groups.Default;

/**
 * 校验分组
 * @author farkle
 * @date 2022-04-09
 */
public interface ValidGroup extends Default {
    //添加
    interface Add extends ValidGroup{
    }
    //更新
    interface Update extends ValidGroup{
    }
    //删除
    interface Del extends ValidGroup{
    }
}
