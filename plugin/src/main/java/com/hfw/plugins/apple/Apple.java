package com.hfw.plugins.apple;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.common.support.UrlHttp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.Map;

@Data
public class Apple {

    private String env;

    /**
     * 构造公钥
     *
     * @param kid A 10-character identifier key, obtained from your developer account.
     * @return {
     * "keys": [
     * {
     * "kty": "RSA",
     * "kid": "86D88Kf",
     * "use": "sig",
     * "alg": "RS256",
     * "n": "iGaLqP6y-SJCCBq5Hv6pGDbG_SQ11MNjH7rWHcCFYz4hGwHC4lcSurTlV8u3avoVNM8jXevG1Iu1SY11qInqUvjJur--hghr1b56OPJu6H1iKulSxGjEIyDP6c5BdE1uwprYyr4IO9th8fOwCPygjLFrh44XEGbDIFeImwvBAGOhmMB2AD1n1KviyNsH0bEB7phQtiLk-ILjv1bORSRl8AK677-1T8isGfHKXGZ_ZGtStDe7Lu0Ihp8zoUt59kx2o9uWpROkzF56ypresiIl4WprClRCjz8x6cPZXU2qNWhu71TQvUFwvIvbkE1oYaJMb0jcOTmBRZA2QuYw-zHLwQ",
     * "e": "AQAB"
     * }
     * ]
     * }
     */
    public PublicKey getPublicKey(String kid) throws Exception {
        String s = UrlHttp.get("https://appleid.apple.com/auth/keys");
        Map<String, Object> info = JSON.parseObject(s, Map.class);
        List<Map<String, Object>> keys = (List<Map<String, Object>>) info.get("keys");
        for (Map<String, Object> key : keys) {
            if (kid.equals(key.get("kid"))) {
                String n = key.get("n").toString();
                String e = key.get("e").toString();
                BigInteger modulus = new BigInteger(1, Base64.decodeBase64(n));
                BigInteger publicExponent = new BigInteger(1, Base64.decodeBase64(e));
                RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
                final KeyFactory kf = KeyFactory.getInstance("RSA");
                return kf.generatePublic(spec);
            }
        }
        return null;
    }

    /**
     * 校验jwt
     *
     * @param publicKey 公钥
     * @param jwt
     * @param iss       发行者注册的声明密钥，其值为。https://appleid.apple.com
     * @param sub       用户的唯一标识符。
     * @param aud       您在您的Apple Developer帐户中。client_id
     * @return
     */
    private boolean verify(PublicKey publicKey, String jwt, String iss, String sub, String aud) throws Exception {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(publicKey).build();
        jwtParser.requireIssuer(iss);
        jwtParser.requireAudience(aud);
        jwtParser.requireSubject(sub);
        Jws<Claims> claim = jwtParser.parseClaimsJws(jwt);
        if (claim != null && claim.getBody().containsKey("auth_time")) {
            return true;
        }
        return false;
    }

    /**
     * 校验 identityToken
     * 返回 苹果用户唯一标识 sub
     *
     * @param identityToken
     * @return jwt 格式 identityToken
     * eyJraWQiOiJBSURPUEsxIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLnNreW1pbmcuZGV2aWNlbW9uaXRvciIsImV4cCI6MTU2NTY2ODA4NiwiaWF0IjoxNTY1NjY3NDg2LCJzdWIiOiIwMDEyNDcuOTNiM2E3OTlhN2M4NGMwY2I0NmNkMDhmMTAwNzk3ZjIuMDcwNCIsImNfaGFzaCI6Ik9oMmFtOWVNTldWWTNkcTVKbUNsYmciLCJhdXRoX3RpbWUiOjE1NjU2Njc0ODZ9.e-pdwK4iKWErr_Gcpkzo8JNi_MWh7OMnA15FvyOXQxTx0GsXzFT3qE3DmXqAar96nx3EqsHI1Qgquqt2ogyj-lLijK_46ifckdqPjncTEGzVWkNTX8uhY7M867B6aUnmR7u-cf2HsmhXrvgsJLGp2TzCI3oTp-kskBOeCPMyTxzNURuYe8zabBlUy6FDNIPeZwZXZqU0Fr3riv2k1NkGx5MqFdUq3z5mNfmWbIAuU64Z3yKhaqwGd2tey1Xxs4hHa786OeYFF3n7G5h-4kQ4lf163G6I5BU0etCRSYVKqjq-OL-8z8dHNqvTJtAYanB3OHNWCHevJFHJ2nWOTT3sbw
     * <p>
     * header 解码
     * {"kid":"AIDOPK1","alg":"RS256"} 其中kid对应上文说的密钥id
     * <p>
     * claims 解码
     * {
     * "iss":"https://appleid.apple.com", 发行者注册的声明密钥，其值为。https://appleid.apple.com
     * "aud":"com.skyming.devicemonitor", 您在您的Apple Developer帐户中。client_id
     * "exp":1565668086,"iat":1565667486,
     * "sub":"001247.93b3a799a7c84c0cb46cd08f100797f2.0704", 用户的唯一标识符。
     * "c_hash":"Oh2am9eMNWVY3dq5JmClbg",
     * "auth_time":1565667486
     * }
     */
    public String verify(String identityToken) throws Exception {
        //identityToken = new String(Base64.decodeBase64(identityToken));
        String[] identityTokens = identityToken.split("\\.");
        Map<String, Object> header = JSONObject.parseObject(new String(Base64.decodeBase64(identityTokens[0]), "UTF-8"));
        Map<String, Object> claims = JSONObject.parseObject(new String(Base64.decodeBase64(identityTokens[1]), "UTF-8"));
        String kid = header.get("kid").toString();
        String iss = claims.get("iss").toString();
        String sub = claims.get("sub").toString();
        String aud = claims.get("aud").toString();

        PublicKey key = getPublicKey(kid);
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        Claims c = jwtParser.parseClaimsJws(identityToken).getBody();
        System.out.println("sub:"+sub);
        System.out.println(c);
        return c.getSubject();
    }

    /**
     * 苹果支付数据校验
     *
     * @param receipt_data
     * @return
     */
    public boolean applePayVerify(String receipt_data) throws Exception {
        //data = {"receipt-data" : "MIIVDAYJKoZIhvcNAQcCoIIU/T..."}
        receipt_data = "{\"receipt-data\":\"" + receipt_data + "\"}";
        // 苹果支付沙箱验证地址
        String url = "https://sandbox.itunes.apple.com/verifyReceipt";
        if ("prod".equals(env)) {
            // 苹果支付正式验证地址
            url = "https://buy.itunes.apple.com/verifyReceipt";
        }
        String s = UrlHttp.postBody(url, receipt_data);
        JSONObject obj = JSON.parseObject(s);
        return obj.getInteger("status") == 0;
    }

}