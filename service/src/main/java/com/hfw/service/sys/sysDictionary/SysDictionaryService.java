package com.hfw.service.sys.sysDictionary;

import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 字典表服务
 * @author farkle
 * @date 2025-03-29
 */
@Service
public class SysDictionaryService {
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    public int add(SysDictionary sysDictionary){
        int res = sysDictionaryMapper.insert(sysDictionary);
        Long pid = sysDictionary.getPid();
        if(pid!=null && pid>0){
            sysDictionaryMapper.updateParentChildrenNum(pid);
        }
        return res;
    }
    public int edit(SysDictionary sysDictionary){
        return sysDictionaryMapper.updateByPk(sysDictionary);
    }
    public Result<Void> del(Long id){
        SysDictionary dict = sysDictionaryMapper.selectByPk(id);
        if(dict.getChildrenNum()>0){
            return Result.error("节点下有子节点,无法删除!");
        }
        int res = sysDictionaryMapper.deleteByPk(id);
        Long pid = dict.getPid();
        if(pid!=null && pid>0){
            sysDictionaryMapper.updateParentChildrenNum(pid);
        }
        return Result.result( res );
    }
    public int dels(List<Long> ids){
        int res = 0;
        Set<Long> pidSet = new HashSet<>();
        for (Long id : ids) {
            SysDictionary dict = sysDictionaryMapper.selectByPk(id);
            if(dict.getChildrenNum()<=0){
                res += sysDictionaryMapper.deleteByPk(id);
                Long pid = dict.getPid();
                if(pid!=null && pid>0){
                    pidSet.add(pid);
                }
            }
        }
        for (Long pid : pidSet) {
            sysDictionaryMapper.updateParentChildrenNum(pid);
        }
        return res;
    }

}
