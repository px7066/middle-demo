package com.github.support.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>bean依赖方法调用</p>
 *
 * @author <a href="mailto:panxi@zjport.gov.cn">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class BeanDepend implements InitializingBean {

    public BeanDepend() {
        System.out.println("方法：调用构造方法" + this.getClass());
    }

    @PostConstruct
    public void init(){
        System.out.println("方法：BeanDepend.init");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("方法：BeanDepend.afterPropertiesSet");
    }


}
