package com.hfw.service.auth;

import com.hfw.model.component.RedisUtil;
import com.hfw.model.utils.IdWorker;
import com.hfw.model.utils.RsaUtil;
import com.hfw.service.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class RedisAuthService implements AuthService {
    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCvxAI1P9wf4v44ilI2ap0p00yRXH5hzNqL7/HZ74xBcuu89TYkuXuUIzgOH4xQsczM4UU9dGMhEoyLqspTKS6eldStoh8Ktlt7jBh87BuScfrHW/G4iKIgfFQtP1HiWSo2IDfeKXVpp7G4nSl8C0MvCO5NEYyMEXf0BAILeQZ/iVcnB7NmojUwd1J/NaDbTvwmJSKuilZuI4y8BVC0FeJ3NF61w8aFoiY+4bod6O+wN9Rtb9CX7kQKLKgbWBgN9qXlUDiEORvt5/bu6ZY+NRBQyeY15LTC17Jh02KgaeUKwYHuLLLpKgvZlGHyeVZUKPc0LJ1heGYJ38pMBYIzFNV/AgMBAAECggEATP+LEBTHs9hXEiHWXixaaUY9lrh0TRfW7dLRmm2MfeeASikJ7RCsiHrdRaKgb4FUfLR7Bq8D9isQEudg9wlleOeeYepRuvmkHf2DhMZSVddEyy0GOZbxn1WX8rJ4hrYU/d0jXFnBK+Xgv0VqNQMiDWrWKkDz1zCX4fgXhTshk6C1xiTv6wgqPBwV9EbnJwHS3ouPbVyt/NB23JFTPqfu9qBchgQrByR2WY2SiWH5ygB/Dn3c8cNIz9/2T66czfFWMu5lB5GZY7EAvJ1Ha4tD8jW890qL+STQbxUqmBGvi8s0BqB8NlVXQDML+ox0oc8CO5O8zVChh+4KfYhy7V9P0QKBgQDuTz2WeWTuqwQFyuRvnPNZ3g90XA+WEyTIUKSckWMa2iW+beh2E8g5WzlkrGG5PvD72WjoAq65cpMCr6HRn+MGsVIh/+DIB473cf/Jiq8DzGsJv7PsUAT5VkjralnVth+pPp9b+qAevrzeAtoypF0n6aPt25Zvi/HtlEz6fj9F2wKBgQC80DQ0N7GJ5NI1f+64h4Umw29rXfILpy0Hv+OacAgiYY2AUdZY4tlS01KgCFLg8uftOHq5bz4y4Gzh5bh7bRzE6ye6w48oP+v+67HkKa6tnKB+7v/D51uri8kCusJl/P3eco6Ul79AiLqx4PVoSjOr9S7a2Vk5zvNZ5RS1GCEKLQKBgAh4Bm9Hhx+4Mh9mk3Gn+TnzW0c+Ukt6CK+Ztg/eVtBIeljnHQC9OTtYk5SRT7O+md/JXSIJnl32xWSuCykg7nd5a7rLlhhltF30zitCY2/WHuOszV8ThOm7eEHteJ3QqBVipVUmdfRBwQJ+xXRRRQI7GllEmXcDWSihQY8RV3xLAoGADO+OxA0UwsfEhxnOtyHBw5gcKBT1okq/0Qoag9gtFn3sfeAdeVWBYiVMQd6QHw1FSoFWFyJB9qZdj1R3wS0GjY8215rSUgWfG4X2WBVq/EYA0PF64GcXZrda/C7jZq1gjz71+cQzvC4h7FHnPk/nHl7b6O5/KHC/R2dkdcvDZ7kCgYBB6JFNVKYZlbcBKRh8CINbxtNN3afT+yGTW7nTDW5bb5n/DuiT2CSKgjVaCnoqFX3Ceyoea5NwWbbOMyJsP9HLcTk9QauvbEH/GFJugodW2qKLWp59A+xNBKxlNxbJDVzA1naVbx35Vfj7tXpra/uz6GuKIooxnpmlbiIod8tW0A==";
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr8QCNT/cH+L+OIpSNmqdKdNMkVx+Yczai+/x2e+MQXLrvPU2JLl7lCM4Dh+MULHMzOFFPXRjIRKMi6rKUykunpXUraIfCrZbe4wYfOwbknH6x1vxuIiiIHxULT9R4lkqNiA33il1aaexuJ0pfAtDLwjuTRGMjBF39AQCC3kGf4lXJwezZqI1MHdSfzWg2078JiUiropWbiOMvAVQtBXidzRetcPGhaImPuG6HejvsDfUbW/Ql+5ECiyoG1gYDfal5VA4hDkb7ef27umWPjUQUMnmNeS0wteyYdNioGnlCsGB7iyy6SoL2ZRh8nlWVCj3NCydYXhmCd/KTAWCMxTVfwIDAQAB";
    private long timeout = 30;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Token genToken(LoginUser loginUser) throws Exception {
        String uuid = IdWorker.uuid();
        String token = RsaUtil.encryptByPrivateKey("RSA", privateKey, uuid);
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(timeout);
        redisUtil.setEx("auth:"+uuid, loginUser, timeout, TimeUnit.MINUTES);
        loginUser.setAccessToken(token);
        loginUser.setExpires(expireTime);
        return new Token(uuid, token, expireTime);
    }

    @Override
    public LoginUser verifyToken(String token) throws Exception {
        if(!StringUtils.hasText(token)){
            return null;
        }
        String uuid = RsaUtil.decryptByPublicKey("RSA", publicKey, token);
        return redisUtil.getEx("auth:"+uuid, timeout, TimeUnit.MINUTES);
    }

    @Override
    public boolean delete(String token){
        return redisUtil.del("auth:"+token);
    }
}
