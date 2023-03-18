package com.hfw.admin.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.hfw.basesystem.config.RedisUtil;
import com.hfw.common.support.IdWorker;
import com.hfw.common.support.fastjson.FastJson;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import com.hfw.model.entity.SysDemo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * excel失败导入处理器
 * @author farkle
 * @date 2023-03-17
 */
@Component
public class ErrorHandler {

    private static final String redis_key = "excel_error:";

    @Resource
    private RedisUtil redisUtil;

    public String save(List<SysDemo> errorList, Map<String, ErrorData> errorMap){
        String id = IdWorker.uuid();
        String key = redis_key+ id;
        redisUtil.setStrEx(key+":list", FastJson.toJSONString(errorList, JSONWriter.Feature.WriteClassName),60*60*24);
        redisUtil.setEx(key+":map", errorMap,60*60*24);
        return id;
    }

    public void download(String id, HttpServletResponse response) throws IOException {
        String key = redis_key+ id;
        String listStr = redisUtil.getStr(key+":list");
        if(!StringUtils.hasText(listStr)){
            RequestUtil.json(response, ApiResult.error("错误报告已经失效!"));
            return ;
        }
        Map<String, ErrorData> errorMap = redisUtil.get(key+":map");;
        List list = JSON.parseArray(listStr, Object.class, JSONReader.Feature.SupportAutoType);
        if(list==null || list.size()<=0 || errorMap==null){
            RequestUtil.json(response, ApiResult.error("错误报告已经失效!"));
            return ;
        }
        OutputStream os = response.getOutputStream();
        try{
            String fileName = URLEncoder.encode("错误报告.xlsx", "UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            EasyExcel.write(os, list.get(0).getClass()).autoCloseStream(false)
                    .registerWriteHandler(new ErrorCellWriteHandler(errorMap)).sheet().doWrite(list);
        }catch (Exception e){
            e.printStackTrace();
            response.reset();
            RequestUtil.json(response, ApiResult.error(e.getMessage()));
        }finally {
            os.close();
        }
    }

}
