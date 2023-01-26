import com.hfw.basesystem.entity.SysUser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author farkle
 * @date 2022-04-12
 */
public class AdminTest extends Object{

    public static void main(String[] args) throws Exception {
        SysUser user = new SysUser();
        System.out.println(user.getClass());
        System.out.println(user.getClass().getSuperclass());
    }

}
