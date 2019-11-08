package com.github.dubbo.producer.config;

import com.github.support.mybatis.plugin.ResultSetHandlerPlugin;
import com.github.support.mybatis.plugin.StatementPlugin;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>mybatis插件配置</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Configuration
public class MybatisPluginConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return configuration -> {
            configuration.addInterceptor(new StatementPlugin());
            configuration.addInterceptor(new ResultSetHandlerPlugin());
        };
    }

}
