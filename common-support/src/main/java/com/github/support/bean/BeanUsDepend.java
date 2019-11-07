package com.github.support.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>上层bean依赖方法调用</p>
 *
 * @author <a href="mailto:panxi@zjport.gov.cn">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class BeanUsDepend implements InitializingBean, ApplicationContextAware {

    @Autowired
    private BeanLifeCycle beanLifeCycle;

    public BeanUsDepend() {
        System.out.println("方法：调用构造方法" + this.getClass());
    }

    @PostConstruct
    public void init(){
        System.out.println("方法：" + this.getClass() + ".init");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("方法：" + this.getClass() + ".afterPropertiesSet");
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("调用方法:" + this.getClass() + "setApplicationContext");
    }
}
