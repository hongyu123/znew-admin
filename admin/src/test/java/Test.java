import cn.dev33.satoken.secure.BCrypt;
import com.hfw.model.enums.sys.Gender;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.utils.BeanUtil;
import com.hfw.service.dto.LoginUser;

public class Test {
    public static void main(String[] args) throws Exception {
        //BeanUtil.genBeanCopy(LoginUser.class, SysUser.class);
// 使用方法
        String origin = BCrypt.hashpw("123456", BCrypt.gensalt(12));
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt(12)));
        System.out.println(origin);
        // 使用checkpw方法检查被加密的字符串是否与原始字符串匹配：
        boolean res = BCrypt.checkpw("123456",origin);
        System.out.println(res);
    }

}
