package com.hfw.service.sys.sysDataScope;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hfw.model.enums.sys.DataScope;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.typehandler.DBList;
import com.hfw.model.po.sys.SysDataScope;
import com.hfw.model.po.sys.SysOrganization;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysOrganization.SysOrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public SysDataScope detail(Long id){
        return sysDataScopeMapper.selectByPk(id);
    }
    public int add(SysDataScope sysDataScope){
        this.clearCache();
        return sysDataScopeMapper.insert(sysDataScope);
    }
    public int edit(SysDataScope sysDataScope){
        this.clearCache();
        return sysDataScopeMapper.updateByPk(sysDataScope);
    }
    public int del(Long id){
        this.clearCache();
        return sysDataScopeMapper.deleteByPk(id);
    }
    public int dels(List<Long> ids){
        this.clearCache();
        return sysDataScopeMapper.deleteByPks(ids);
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
            sysOrg = sysOrganizationMapper.selectByPk(orgId);
        }
        List<SysDataScope> dataScopeList = sysDataScopeMapper.selectList(Where.<SysDataScope>where().eq(SysDataScope.COLUMN.dataKey,key));
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
            if (DataScope.OrganizationAndChildren==dataScope.getDataScope() && sysOrg!=null){
                List<SysOrganization> orgTree = sysOrganizationMapper.orgTreeList(sysOrg.getAncestors(), EnableState.Enable);
                orgTree.add(sysOrg);
                List<String> list = orgTree.stream().map(item->String.valueOf(item.getId())).toList();
                dataScope.setCustomIds(new DBList(list));
            }
        }
        loginUser.setDataScopeKey(dataScopeKey);
        return dataScope;
    }

}
