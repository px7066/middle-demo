package com.github.dubbo.producer.config;

import com.github.support.handle.LifeCycleHandlerInterceptorAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * <p>拦截器配置</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LifeCycleHandlerInterceptorAdapter lifeCycleHandlerInterceptorAdapter = new LifeCycleHandlerInterceptorAdapter();
        registry.addInterceptor(lifeCycleHandlerInterceptorAdapter);
    }
}
