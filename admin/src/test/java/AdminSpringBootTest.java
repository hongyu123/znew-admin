import com.hfw.admin.AdminApplication;
import com.hfw.basesystem.gen.GenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author zyh
 * @date 2022-04-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminApplication.class})
public class AdminSpringBootTest {

    @Autowired
    private GenService genService;

    @Test
    //代码生成
    public void gen() throws Exception {
        //genService.genAll("entertainment");
        genService.gen("app_advice");
        genService.gen("app_article");
        genService.gen("app_version");
    }

}
