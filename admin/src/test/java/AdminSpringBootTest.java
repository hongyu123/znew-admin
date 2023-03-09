import com.hfw.admin.AdminApplication;
import com.hfw.basesystem.config.ScanSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * @author farkle
 * @date 2022-04-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminApplication.class})
public class AdminSpringBootTest {

    @Resource
    private ScanSupport scanSupport;

    @Test
    public void test() throws Exception {
        scanSupport.scan("com.hfw.common.enums");
        scanSupport.scan("com.hfw.basesystem.enums");
        scanSupport.scan("com.hfw.model.enums");
    }

}
