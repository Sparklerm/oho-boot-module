package com.oho.redis.core.config;

import com.oho.common.utils.JsonUtils;
import com.oho.redis.core.utils.RedisDistributedId;
import com.oho.redis.core.utils.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Sparkler
 * @createDate 2022/11/24
 */
@Import({RedisUtil.class})
@Configuration
public class RedisTemplateConfig {

    @Bean("comRedisTemplate")
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //  配置连接工厂
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        objectJackson2JsonRedisSerializer.setObjectMapper(JsonUtils.objectMapper);

        // 配置key的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // 使用Jackson2JsonRedisSerializer配置value的序列化方式
        template.setValueSerializer(objectJackson2JsonRedisSerializer);

        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 注册分布式id生成器
     */
    @Bean
    public RedisDistributedId redisDistributedId() {
        return new RedisDistributedId();
    }
}