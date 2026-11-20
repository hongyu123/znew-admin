package com.hfw.admin.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hfw.model.component.RedisUtil;
import com.hfw.model.jackson.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

/**
 * redis配置
 * @author farkle
 * @date 2022-04-09
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class) // 仅作示例, 生产环境应收紧范围
                //.allowIfSubType("com.hfw.") //生产环境适用, 遵循“最小权限”原则
                .build();
        JsonMapper jsonMapper = JsonUtil.builder()
                .activateDefaultTyping(typeValidator, DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY) //序列化时带有类型信息
                .build();
        JacksonJsonRedisSerializer<Object> jacksonJsonRedisSerializer = new JacksonJsonRedisSerializer<>(jsonMapper, Object.class);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jacksonJsonRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate){
        return new RedisUtil(redisTemplate, stringRedisTemplate);
    }

}
