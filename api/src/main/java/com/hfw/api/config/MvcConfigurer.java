package com.hfw.api.config;

import com.hfw.api.support.AuthenticationInterceptor;
import com.hfw.api.support.CorsAndAuthInterceptor;
import com.hfw.api.support.RequestJsonFilter;
import com.hfw.basesystem.support.DateConverter;
import com.hfw.basesystem.support.NullHandleMappingJackson2HttpMessageConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author zhuyuhong
 * @create 2020-03-03
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    /**
     * 访问swagger文档
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/doc.html");
    }

    /*自定义过滤器
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestJsonFilter());
        registration.addUrlPatterns("/*");//过滤的路径
        registration.setName("requestJsonFilter");
        //registration.setOrder(1);
        return registration;
    }*/

    //api接口认证过滤器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor( new CorsAndAuthInterceptor() );
        interceptorRegistration.addPathPatterns("/**");

        InterceptorRegistration auth = registry.addInterceptor(  new AuthenticationInterceptor() );
        auth.addPathPatterns("/user/**");
        //auth.excludePathPatterns("/","/doc.html","/webjars/**");
        //auth.excludePathPatterns("/auth/**","/view/**","/common/**","/notify/**","/test/**");
    }

    //jackson返回配置
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new NullHandleMappingJackson2HttpMessageConverter());
    }*/

    //jackson返回配置
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        return new NullHandleMappingJackson2HttpMessageConverter();
    }
    //日期类型参数转换配置
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

}
