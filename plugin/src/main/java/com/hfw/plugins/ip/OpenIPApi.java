package com.hfw.plugins.ip;

import com.alibaba.fastjson2.JSON;
import com.hfw.common.support.UrlHttp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author farkle
 * @date 2022-12-19
 */
@Slf4j
public class OpenIPApi {
    @Data
    static class BaiduApiResult {
        private String status;
        private List<BaiduApiData> data;
    }

    @Data
    static class BaiduApiData {
        private String location;
    }

    /**
     * 百度IP归属地查询的接口
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public static String baidu(String ip){
        try{
            String res = UrlHttp.get("https://opendata.baidu.com/api.php?query=" + ip + "&resource_id=6006&co=&oe=utf8");
            BaiduApiResult result = JSON.parseObject(res, BaiduApiResult.class);
            if ("0".equals(result.status) && result.data != null && result.data.size() > 0) {
                return result.data.get(0).location;
            }
        }catch (Exception e){
            log.error("百度IP归属地查询c错误",e);
        }
        return "";
    }

    /**
     * 其它免费查询接口
     * https://ip.useragentinfo.com/json?ip=220.163.124.202
     * https://api.vore.top/api/IPdata?ip=220.163.124.202
     */
}
