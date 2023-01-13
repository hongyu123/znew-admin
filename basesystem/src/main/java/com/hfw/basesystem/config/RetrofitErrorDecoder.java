package com.hfw.basesystem.config;

import com.github.lianjiatech.retrofit.spring.boot.core.ErrorDecoder;
import com.github.lianjiatech.retrofit.spring.boot.exception.RetrofitException;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * @author farkle
 * @date 2022-11-09
 */
@Component
public class RetrofitErrorDecoder implements ErrorDecoder {

    @Override
    public RuntimeException invalidRespDecode(Request request, Response response) {
        //401未认证和正常请求不抛出异常
        if(response.code()==401 || response.code()==200){
            return null;
        }
        throw RetrofitException.errorStatus(request, response);
    }

}
