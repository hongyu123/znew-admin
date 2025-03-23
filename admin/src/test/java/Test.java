import cn.dev33.satoken.secure.BCrypt;
import com.hfw.model.enums.sys.Gender;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.utils.BeanUtil;
import com.hfw.model.utils.RsaUtil;
import com.hfw.model.utils.SignUtil;
import com.hfw.service.dto.LoginUser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Test {
    public static void main(String[] args) throws Exception {
        //BeanUtil.genBeanCopy(LoginUser.class, SysUser.class);
        RsaUtil rsa = new RsaUtil();
        String data = "1";
        rsa.generateKey();
        System.out.println(rsa.getPublicKey());
        System.out.println(rsa.getPrivateKey());
        String ciphertext = rsa.encryptByPrivateKey(data);
        System.out.println(ciphertext);
        System.out.println(rsa.decryptByPublicKey(ciphertext));
    }

}
