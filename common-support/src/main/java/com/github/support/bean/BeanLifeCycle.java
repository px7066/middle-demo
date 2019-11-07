package com.github.support.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>bean生命周期</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class BeanLifeCycle implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor,InitializingBean{

    @Autowired
    private BeanDepend beanDepend;

    public BeanLifeCycle() {
        System.out.println("方法：调用构造方法" + this.getClass());
    }

    @PostConstruct
    public void init(){
        System.out.println("方法：" + this.getClass() + ".init");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("方法：" + this.getClass() + ".setBeanName");
        System.out.println(name);
    }



    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("方法：" + this.getClass() + ".setBeanFactory");
        System.out.println(beanFactory.toString());
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("方法：" + this.getClass() + ".setApplicationContext");
        BeanLifeCycle beanLifeCycle = applicationContext.getBean(BeanLifeCycle.class);
        System.out.println(beanLifeCycle.toString());
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("方法：" + this.getClass() + ".postProcessBeforeInitialization" + ":" + beanName);
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("方法："+ this.getClass()+".postProcessAfterInitialization" + ":" + beanName);
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("方法：" + this.getClass() + ".afterPropertiesSet");
    }


}
