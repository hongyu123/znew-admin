import com.alibaba.fastjson2.JSON;
import com.hfw.model.entity.AppUser;

/**
 * @author zyh
 * @date 2022-11-26
 */
public class ApiTest {

    public static void main(String[] args) throws Exception {
        String s = "性别xxx";
        s = s.replaceAll("\\(.+\\)","");
        System.out.println(s);
    }
}
