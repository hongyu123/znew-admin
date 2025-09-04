package com.hfw.service.sys.sysOrganization;

import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysOrganization;
import com.hfw.model.utils.TreeUtil;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysDataScope.SysDataScopeService;
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
    @Autowired
    private SysDataScopeService sysDataScopeService;

    public Page<SysOrganization> page(Page<SysOrganization> page, SysOrganization po) {
        return sysOrganizationMapper.page(page, po);
    }
    public SysOrganization detail(Long id){
        return sysOrganizationMapper.getById(id);
    }

    @Transactional
    public Result<Void> add(SysOrganization sysOrganization){
        SysOrganization parent = null;
        if(sysOrganization.getPid()!=null && sysOrganization.getPid()>0){
            parent = sysOrganizationMapper.getById(sysOrganization.getPid());
            if(parent==null){
                return Result.error("父节点不存在!");
            }
        }
        sysOrganization.setAncestors("");
        sysOrganizationMapper.save(sysOrganization);
        String ancestors = parent==null ?String.valueOf(sysOrganization.getId()) :parent.getAncestors()+","+sysOrganization.getId();
        sysOrganization.setAncestors(ancestors);
        sysOrganizationMapper.update(sysOrganization);
        sysDataScopeService.clearCache();
        return Result.success();
    }

    public Result<Void> edit(SysOrganization sysOrganization){
        SysOrganization parent = null;
        if(sysOrganization.getPid()!=null && sysOrganization.getPid()>0){
            if(sysOrganization.getId().equals(sysOrganization.getPid())){
                return Result.error("不能设置父节点为自己");
            }
            parent = sysOrganizationMapper.getById(sysOrganization.getPid());
            if(parent==null){
                return Result.error("父节点不存在!");
            }
        }
        String ancestors = parent==null ?String.valueOf(sysOrganization.getId()) :parent.getAncestors()+","+sysOrganization.getId();
        sysOrganization.setAncestors(ancestors);
        sysDataScopeService.clearCache();
        return Result.result( sysOrganizationMapper.update(sysOrganization) );
    }

    public Result<Void> del(Long id){
        Integer cnt = QueryChain.of(sysOrganizationMapper).eq(SysOrganization::getPid, id).count();
        if(cnt>0){
            return Result.error("节点下有子节点,无法删除!");
        }
        sysDataScopeService.clearCache();
        return Result.result( sysOrganizationMapper.deleteById(id) );
    }

    /**
     * 获取当前用户的组织机构树
     * @param state 状态
     * @return 组织机构树
     */
    public List<SysOrganization> selfOrgTree(EnableState state){
        LoginUser loginUser = LoginUser.getLoginUser();
        if(loginUser.getId()==1){
            List<SysOrganization> list = QueryChain.of(sysOrganizationMapper).eq(state!=null, SysOrganization::getState, state).orderBy(SysOrganization::getSort).list();
            return TreeUtil.listToTree(list, SysOrganization::getId, SysOrganization::getPid, SysOrganization::setChildren,
                    (dto) -> dto.getPid()==0 );
        }else if(loginUser.getOrgId()==null){
            return null;
        }else{
            SysOrganization org = sysOrganizationMapper.getById(loginUser.getOrgId());
            if(org==null){
                return null;
            }
            List<SysOrganization> list = sysOrganizationMapper.orgTreeList(org.getAncestors(), state);
            list.add(org);
            return TreeUtil.listToTree(list, SysOrganization::getId, SysOrganization::getPid, SysOrganization::setChildren,
                    (dto) -> dto.getId().equals(org.getId()) );
        }
    }

}
