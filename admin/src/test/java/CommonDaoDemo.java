import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.mybatis.Condition;
import com.hfw.common.entity.PageResult;
import com.hfw.common.enums.Gender;
import com.hfw.common.enums.SortByWay;
import com.hfw.model.entity.SysDemo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CommonDao使用示例
 * @author farkle
 * @date 2023-01-13
 */
@Service
public class CommonDaoDemo {
    @Resource
    private CommonDao commonDao;

    //主键查找
    public SysDemo selectByPk(Long id){
        return commonDao.selectByPk(SysDemo.class, id);
    }
    //查询所有数据
    public List<SysDemo> select(){
        return commonDao.select(SysDemo.class);
    }
    //查询一条数据,推荐
    public SysDemo findByName(String name){
        return commonDao.selectOne( SysDemo.builder().name(name).build() );
    }
    //查询一条数据2
    public SysDemo findByName2(String name){
        return commonDao.selectOneCond(Condition.create(SysDemo.class).put(SysDemo::getName, name));
    }
    //列表查询,推荐
    public List<SysDemo> list(String name){
        return commonDao.select( SysDemo.builder().name(name).build() );
    }
    //列表查询2
    public List<SysDemo> list2(String name){
        return commonDao.selectCond(Condition.create(SysDemo.class).put(SysDemo::getName, name));
    }
    /**
     * 分页查询,推荐
     * 这里只是简单分页,面对复杂查询的分页推荐使用pagehelper
     * @param name
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<SysDemo> listPage(String name, Integer pageNumber, Integer pageSize){
        SysDemo cond = SysDemo.builder().name(name).build();
        cond.setSortByField("id").setSortByWay(SortByWay.desc);
        return commonDao.select(cond, pageNumber,pageSize);
    }
    //分页查询2
    public List<SysDemo> listPage2(String name, Integer pageNumber, Integer pageSize){
        return commonDao.selectCond(Condition.create(SysDemo.class).put(SysDemo::getName, name), pageNumber,pageSize);
    }
    //统计,推荐
    public Long count(String name){
        return commonDao.count( SysDemo.builder().name(name).build() );
    }
    //统计2
    public Long count2(String name){
        return commonDao.countCond(Condition.create(SysDemo.class).put(SysDemo::getName, name));
    }
    //带统计数量的分页,推荐
    public PageResult<SysDemo> page(String name, Integer pageNumber, Integer pageSize){
        SysDemo cond = SysDemo.builder().name(name).build();
        cond.setSortByField("id").setSortByWay(SortByWay.desc);
        PageResult<SysDemo> pageResult = commonDao.page(cond, pageNumber, pageSize);
        System.out.println(pageResult.getTotal());
        System.out.println(pageResult.getData());
        return pageResult;
    }
    //带统计数量的分页2
    public PageResult<SysDemo> page2(String name, Integer pageNumber, Integer pageSize){
        PageResult<SysDemo> pageResult = commonDao.pageCond(Condition.create(SysDemo.class).put(SysDemo::getName, name), pageNumber,pageSize);
        System.out.println(pageResult.getTotal());
        System.out.println(pageResult.getData());
        return pageResult;
    }
    //新增
    public int insert(){
        SysDemo sysDemo = new SysDemo();
        sysDemo.setName("小明");
        return commonDao.insert(sysDemo);
    }
    //批量新增
    public int insertBatch(){
        List<SysDemo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SysDemo sysDemo = new SysDemo();
            sysDemo.setName("小明"+i);
            list.add(sysDemo);
        }
        return commonDao.insertBatch(list);
    }
    //主键更新
    public int updateByPk(Long id){
        SysDemo sysDemo = new SysDemo();
        sysDemo.setId(id);
        sysDemo.setName("小明名称变更");
        return commonDao.updateByPk(sysDemo);
    }
    //updateAllField 根据主键根性所有字段

    //条件更新
    public int update(){
        SysDemo update = new SysDemo();
        update.setName("所有男的都叫小明");
        return commonDao.update(update, SysDemo.builder().gender(Gender.Male).build() );
    }
    //主键删除
    public int deleteByPk(Long id){
        return commonDao.deleteByPk(SysDemo.class, id);
    }
    //主键批量删除
    public int deleteBatch(List<Long> ids){
        return commonDao.deleteBatch(SysDemo.class, ids);
    }
    //条件删除
    public int delete(){
        //删除所有男性
        return commonDao.delete( SysDemo.builder().gender(Gender.Male).build() );
    }
}
