package com.hfw.plugins.weixin;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.common.support.UrlHttp;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zyh
 * @date 2022-11-26
 */
@Data
public class WeixinSNS {

    private String appid;
    private String secret;
    /**
     * 通过 code 获取 access_token
     * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Authorized_API_call_UnionID.html
     * @param code
     * @return
     * @throws Exception
     */
    public JSONObject access_token(String code) throws Exception {
        String url = "GET https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        String res = UrlHttp.get(url);
        JSONObject obj = JSON.parseObject(res);
        if (StringUtils.isBlank(obj.getString("openid")) || StringUtils.isBlank(obj.getString("access_token"))) {
            throw new RuntimeException(obj.getString("errmsg"));
        }
        return obj;
    }

    /**
     * 获取用户个人信息（UnionID 机制）
     * https://developers.weixin.qq.com/doc/oplatform/Mobile_App/WeChat_Login/Authorized_API_call_UnionID.html
     * @param openid
     * @param access_token
     * @return
     * @throws Exception
     */
    public JSONObject userinfo(String openid, String access_token) throws Exception {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
        String res = UrlHttp.get(url);
        JSONObject info = JSON.parseObject(res);
        if (StringUtils.isBlank(info.getString("openid"))) {
            throw new RuntimeException(info.getString("errmsg"));
        }
        return info;
    }


}
