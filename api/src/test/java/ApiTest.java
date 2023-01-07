import com.alibaba.fastjson2.JSON;
import com.hfw.model.entity.AppUser;

import java.util.UUID;

/**
 * @author zyh
 * @date 2022-11-26
 */
public class ApiTest {

    public static void main(String[] args) throws Exception {
        System.out.println("mysql_"+ UUID.randomUUID().toString());
        System.out.println("redis_"+ UUID.randomUUID().toString());
    }
}
