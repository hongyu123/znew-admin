package com.hfw.model.plugins.ip;

import com.alibaba.fastjson2.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * @author farkle
 * @date 2022-12-19
 */
@Slf4j
public class OpenIP {
    @Getter @Setter
    static class BaiduApiResult {
        private String status;
        private List<BaiduApiData> data;
    }

    @Getter @Setter
    static class BaiduApiData {
        private String location;
    }

    /**
     * 百度IP归属地查询的接口
     * @param ip
     * @return
     * @throws Exception
     */
    public static String baidu(String ip){
        String url = "https://opendata.baidu.com/api.php?query=" + ip + "&resource_id=6006&co=&oe=utf8";
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body();
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
