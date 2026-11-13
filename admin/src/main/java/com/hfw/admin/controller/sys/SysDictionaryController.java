package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.po.sys.SysDictionary;
import com.hfw.service.component.CommonService;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysDictionary.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典表Controller
 * @author  farkle
 * @date 2025-03-29
 */
@RestController
@RequestMapping("/sysDictionary")
public class SysDictionaryController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @GetMapping("/page")
    public PageResult<SysDictionary> page(Page<SysDictionary> page, SysDictionary po) {
        boolean dictKey = StringUtils.hasText(po.getDictKey());
        boolean dictValue = StringUtils.hasText(po.getDictValue());
        Where<SysDictionary> where = Where.<SysDictionary>where()
                .eq(SysDictionary.COLUMN.leafFlag, 0)
                // 有查询条件时，不限制pid
                .eq(!dictKey && !dictValue, SysDictionary.COLUMN.pid, 0)
                .like(dictKey, SysDictionary.COLUMN.dictKey, po.getDictKey() + "%")
                .like(dictValue, SysDictionary.COLUMN.dictValue, po.getDictValue() + "%")
                .orderBy(SysDictionary.COLUMN.sort, Sort.DESC);
        return commonService.page(SysDictionary.class, where, page);
    }

    @GetMapping("/children")
    public Result<List<SysDictionary>> children(@RequestParam Long id, Integer leafFlag){
        List<SysDictionary> list = commonService.selectList(SysDictionary.class, Where.<SysDictionary>where().eq(SysDictionary.COLUMN.pid, id).eq(leafFlag!=null, SysDictionary.COLUMN.leafFlag, leafFlag));
        return Result.success( list );
    }

    @GetMapping
    public Result<SysDictionary> detail(@RequestParam Long id){
        SysDictionary po = commonService.selectByPk(SysDictionary.class, id);
        return Result.success( po );
    }

    @AdminLog("新增字典表")
    @PostMapping
    public Result<Void> add(@RequestBody SysDictionary sysDictionary){
        return Result.result(sysDictionaryService.add(sysDictionary));
    }

    @AdminLog("编辑字典表")
    @PutMapping
    public Result<Void> edit(@RequestBody SysDictionary sysDictionary){
        return Result.result(sysDictionaryService.edit(sysDictionary));
    }

    @AdminLog("删除字典表")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return sysDictionaryService.del(id);
    }

    @AdminLog("批量删除字典表")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        return Result.result(sysDictionaryService.dels(ids));
    }

    // 根据key查询数据字典
    @GetMapping("/dict")
    public Result<?> dict(@RequestParam String key){
        return Result.success( sysDictionaryService.dictList(key) );
    }
    @GetMapping("/dptDictList")
    public Result<?> dptDictList(@RequestParam String key){
        LoginUser loginUser = LoginUser.getLoginUser();
        if(loginUser.isAdmin()){
            return Result.success( sysDictionaryService.dictList(key) );
        }
        return Result.success( sysDictionaryService.dptDictList(key, loginUser.getOrgId()) );
    }
}
