package com.hfw.api.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.common.util.RequestUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * json请求添加请求参数拦截器
 * @author farkle
 * @date 2022-11-07
 */
public class RequestJsonFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!RequestUtil.isJson(request)){
            filterChain.doFilter(request, response);
            return ;
        }
        String body = IOUtils.toString(request.getInputStream(), "utf-8");
        JSONObject obj = JSON.parseObject(body);
        JsonHttpServletRequestWrapper requestWrapper = new JsonHttpServletRequestWrapper(request, body);
        obj.forEach( (k,v) -> {
            requestWrapper.setParameter(k,v.toString());
        });
        filterChain.doFilter(requestWrapper, response);
    }

    private class JsonHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, String> params = new HashMap<>();
        String body;
        JsonHttpServletRequestWrapper(HttpServletRequest request, String body) {
            super(request);
            this.body = body;
        }
        void setParameter(String k, String v){
            params.put(k,v);
        }

        @Override
        public String getParameter(String k) {
            String param = super.getParameter(k);
            if(StringUtils.hasText(param)){
              return param;
            }
            return this.params.get(k);
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] parameterValues = super.getParameterValues(name);
            if(parameterValues!=null){
                return parameterValues;
            }
            return new String[]{params.get(name)};
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes("utf-8"));
            return new JsonInputStream(bis);
        }
    }

    private class JsonInputStream extends ServletInputStream{
        private ByteArrayInputStream bis ;
        private JsonInputStream(ByteArrayInputStream bis){
            this.bis = bis;
        }
        @Override
        public boolean isFinished() {
            return bis.available() <= 0;
        }

        @Override
        public boolean isReady() {
            return bis.available() > 0;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }
    }
}
