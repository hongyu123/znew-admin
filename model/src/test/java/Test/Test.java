package Test;

import com.hfw.basesystem.util.BeanCopierUtil;
import com.hfw.common.util.BeanUtil;
import com.hfw.model.entity.SysDemo;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author farkle
 * @date 2022-04-11
 */
public class Test {

    public static void main(String[] args) throws Exception {
        SysDemo sysDemo = new SysDemo();
        sysDemo.setId(1L);
        sysDemo.setName("1");
        SysDemo demo = new SysDemo();
        demo.setId(2L);

        //BeanUtil.copyNotNullProperties(sysDemo,demo);
        BeanUtils.copyProperties(sysDemo ,demo);
        System.out.println(sysDemo);
        System.out.println(demo);
    }

}
