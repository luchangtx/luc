package com.mrl.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.mrl.common.service.RedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author luc
 * @date 2020/7/2218:39
 */
public class LettuceRedisConfigure {
    /**
     * 自定义一个泛型为 RedisTemplate<String,Object> 的对象，指定序列化方式
     * key 采用String序列化方式
     * value 采用jackon方式，内部采用ObjectMapper来序列化对象
     * @param factory
     * @return
     */
    @Bean
    @ConditionalOnClass(RedisOperations.class) //表示只有当项目引入spring-boot-starter-data-redis依赖的时候才会注册
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //获取redis连接初始化
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(factory);

        //定义jackjson序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper=new ObjectMapper();

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        //定义string序列化方式
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        //key采用string序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hask key也采用string序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash value采用jackson序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 注册redisService操作服务类对象，在redisTemplate对象注册之后
     * @return
     */
    @Bean
    @ConditionalOnBean(name = "redisTemplate")
    public RedisService redisService(){
        return new RedisService();
    }
}
