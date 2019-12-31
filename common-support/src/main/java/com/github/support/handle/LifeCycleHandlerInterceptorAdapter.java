package com.github.support.handle;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <p></p>
 *
 * @author <a href="mailto:panxi@zjport.gov.cn">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class LifeCycleHandlerInterceptorAdapter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println(this.getClass() + ".preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(this.getClass() + ".postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(this.getClass() + ".afterCompletion");
    }
}
