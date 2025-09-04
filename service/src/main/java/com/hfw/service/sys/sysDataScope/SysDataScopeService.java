package com.hfw.service.sys.sysDataScope;

import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.DataScope;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.po.sys.SysDataScope;
import com.hfw.model.po.sys.SysOrganization;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysOrganization.SysOrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 数据权限表服务
 * @author farkle
 * @date 2025-03-30
 */
@Service
public class SysDataScopeService {
    private final Cache<String, SysDataScope> cache = Caffeine.newBuilder().maximumSize(10240).expireAfterWrite(24, TimeUnit.HOURS).build();
    @Autowired
    private SysDataScopeMapper sysDataScopeMapper;
    @Autowired
    public SysOrganizationMapper sysOrganizationMapper;

    public Page<SysDataScope> page(Page<SysDataScope> page, SysDataScope po) {
        return sysDataScopeMapper.page(page, po);
    }
    public SysDataScope detail(Long id){
        return sysDataScopeMapper.getById(id);
    }

    public int add(SysDataScope sysDataScope){
        this.clearCache();
        return sysDataScopeMapper.save(sysDataScope);
    }
    public int edit(SysDataScope sysDataScope){
        this.clearCache();
        return sysDataScopeMapper.update(sysDataScope);
    }
    public int del(Long id){
        this.clearCache();
        return sysDataScopeMapper.deleteById(id);
    }
    public int dels(List<Long> ids){
        this.clearCache();
        return sysDataScopeMapper.deleteByIds(ids);
    }

    public void clearCache(){
        cache.invalidateAll();
    }
    public SysDataScope recentDataScopeByCache(String key, Long userId, Long orgId) {
        SysDataScopeService service = this;
        return cache.get(userId + "_" + key, cacheKey -> {
            SysDataScope dataScope = service.recentDataScope(key, userId, orgId);
            if (dataScope == null) {
                dataScope = new SysDataScope();
                dataScope.setId(0L);
            }
            return dataScope;
        });
    }

    /**
     * 获取当前用户距离自己最近的数据权限
     * @param key 数据权限key
     */
    private SysDataScope recentDataScope(String key, Long userId, Long orgId){
        LoginUser loginUser = LoginUser.getLoginUser();
        String dataScopeKey = loginUser.getDataScopeKey();
        loginUser.setDataScopeKey(null);
        SysOrganization sysOrg = null;
        if(orgId!=null){
            sysOrg = sysOrganizationMapper.getById(orgId);
        }
        List<SysDataScope> dataScopeList = QueryChain.of(sysDataScopeMapper).eq(SysDataScope::getDataKey, key).list();
        Map<String, SysDataScope> dataScopeMap = dataScopeList.stream().collect(Collectors.toMap(dataScope->dataScope.getConfigType()+"_"+dataScope.getConfigId(), dataScope->dataScope, (old, current) -> current));
        SysDataScope dataScope = dataScopeMap.get("1_" + userId);
        if(dataScope==null && sysOrg!=null){
            String ancestors = sysOrg.getAncestors();
            String[] ancestorArr = ancestors.split(",");
            for (int i = ancestorArr.length-1; i >-1 ; i--) {
                dataScope = dataScopeMap.get("2_" + ancestorArr[i]);
                if(dataScope!=null){
                    break;
                }
            }
        }
        if(dataScope!=null){
            if(DataScope.Custom==dataScope.getDataScope() && StringUtils.hasText(dataScope.getCustomIds())){
                List<Long> idList = JSON.parseArray(dataScope.getCustomIds(), Long.class);
                dataScope.setCustomIds(org.apache.commons.lang3.StringUtils.join(idList,","));
            }else if (sysOrg!=null && DataScope.OrganizationAndChildren==dataScope.getDataScope()){
                List<SysOrganization> orgTree = sysOrganizationMapper.orgTreeList(sysOrg.getAncestors(), EnableState.Enable);
                orgTree.add(sysOrg);
                String ids = orgTree.stream().map(item -> String.valueOf(item.getId())).collect(Collectors.joining(","));
                dataScope.setCustomIds(ids);
            }
        }
        loginUser.setDataScopeKey(dataScopeKey);
        return dataScope;
    }

}
