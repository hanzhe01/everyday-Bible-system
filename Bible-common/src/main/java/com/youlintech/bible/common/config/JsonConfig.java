package com.youlintech.bible.common.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author oyhz
 * 因为JavaScript 的 number 类型的数值范围是 -253~253（不包含边界），
 * JavaScript 不支持后台返回的 Long 类型，所以大于 9007199254740991 的数，
 * 进制转换会存在精度问题，而雪花ID生成的数值过大，导致 JavaScript 不能正常存储导致。
 */
@Configuration
public class JsonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // Long 会自定转换成 String
            builder.serializerByType(Long.class, ToStringSerializer.instance);
        };
    }

}
