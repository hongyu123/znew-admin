package com.hfw.service.sys.sysOrganization;

import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysOrganization;
import com.hfw.model.utils.TreeUtil;
import com.hfw.service.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织机构服务
 * @author farkle
 * @date 2025-03-16
 */
@Service
public class SysOrganizationService {
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    public Page<SysOrganization> page(Page<SysOrganization> page, SysOrganization po) {
        return sysOrganizationMapper.page(page, po);
    }
    public SysOrganization detail(Long id){
        return sysOrganizationMapper.getById(id);
    }

    @Transactional
    public Result<Void> add(SysOrganization sysOrganization){
        String ancestors = "";
        if(sysOrganization.getPid()!=null && sysOrganization.getPid()>0){
            SysOrganization parent = sysOrganizationMapper.getById(sysOrganization.getPid());
            if(parent==null){
                return Result.error("父节点不存在!");
            }
            ancestors = parent.getAncestors();
        }
        sysOrganization.setAncestors("");
        sysOrganizationMapper.save(sysOrganization);
        ancestors += sysOrganization.getId() + ",";
        sysOrganization.setAncestors(ancestors);
        sysOrganizationMapper.update(sysOrganization);
        return Result.success();
    }

    public Result<Void> edit(SysOrganization sysOrganization){
        String ancestors = "";
        if(sysOrganization.getPid()!=null && sysOrganization.getPid()>0){
            if(sysOrganization.getId().equals(sysOrganization.getPid())){
                return Result.error("不能设置父节点为自己");
            }
            SysOrganization parent = sysOrganizationMapper.getById(sysOrganization.getPid());
            if(parent==null){
                return Result.error("父节点不存在!");
            }
            ancestors = parent.getAncestors();
        }
        ancestors += sysOrganization.getId() + ",";
        sysOrganization.setAncestors(ancestors);
        return Result.result( sysOrganizationMapper.update(sysOrganization) );
    }

    public Result<Void> del(Long id){
        Integer cnt = QueryChain.of(sysOrganizationMapper).eq(SysOrganization::getPid, id).count();
        if(cnt>0){
            return Result.error("节点下有子节点,无法删除!");
        }
        return Result.result( sysOrganizationMapper.deleteById(id) );
    }

    /**
     * 获取当前用户的组织机构树
     * @param state 状态
     * @return 组织机构树
     */
    public List<SysOrganization> selfOrgTree(EnableState state){
        LoginUser loginUser = LoginUser.getLoginUser();
        List<SysOrganization> list = null;
        if(loginUser.getId()==1){
            list = QueryChain.of(sysOrganizationMapper).eq(state!=null, SysOrganization::getState, state).orderBy(SysOrganization::getSort).list();
        }else if(loginUser.getOrgId()==null){
            return null;
        }else{
            list = sysOrganizationMapper.orgTreeList(loginUser.getOrgId(), state);
        }
        return TreeUtil.listToTree(list, SysOrganization::getId, SysOrganization::getPid, SysOrganization::setChildren,
                (dto) -> dto.getPid()==0 );
    }

}
