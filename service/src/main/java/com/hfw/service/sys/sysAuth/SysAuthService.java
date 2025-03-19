package com.hfw.service.sys.sysAuth;

import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.SortByWay;
import com.hfw.model.enums.sys.SysAuthEnum;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysAuth;
import com.hfw.model.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统权限服务
 * @author farkle
 * @date 2022-12-14
 */
@Service
public class SysAuthService {
    @Autowired
    private SysAuthMapper sysAuthMapper;

    public Page<SysAuth> page(Page<SysAuth> page, SysAuth po) {
        return sysAuthMapper.page(page, po);
    }

    public SysAuth detail(Long id){
        return sysAuthMapper.getById(id);
    }

    public int add(SysAuth sysAuth){
        return sysAuthMapper.save(sysAuth);
    }
    public Result<Void> edit(SysAuth sysAuth){
        if(sysAuth.getId().equals(sysAuth.getParentId())){
            return Result.error("不能设置父节点为自己");
        }
        return Result.result( sysAuthMapper.update(sysAuth) );
    }
    public Result<Void> del(Long id){
        Integer cnt = QueryChain.of(sysAuthMapper).eq(SysAuth::getParentId, id).count();
        if(cnt>0){
            return Result.error("节点下有子节点,无法删除!");
        }
        return Result.result( sysAuthMapper.deleteById(id) );
    }

    public List<SysAuth> treeList(EnableState state){
        List<SysAuth> list = QueryChain.of(sysAuthMapper).eq(SysAuth::getState, EnableState.Enable).orderBy(SysAuth::getSort).list();
        return TreeUtil.listToTree(list, SysAuth::getId, SysAuth::getParentId, SysAuth::setChildren,
                (dto) -> dto.getParentId()==0 );
    }

    public List<MenuVO> menus(){
        List<SysAuth> list = QueryChain.of(sysAuthMapper).eq(SysAuth::getState, EnableState.Enable).in(SysAuth::getAuthType, SysAuthEnum.Dir, SysAuthEnum.Menu).orderBy(SysAuth::getSort).list();
        List<MenuVO> menus = list.stream().map(MenuVO::of).toList();
        return TreeUtil.listToTree(menus, MenuVO::getId, MenuVO::getParentId, MenuVO::setChildren,
                (dto) -> dto.getParentId()==0 );
    }

}