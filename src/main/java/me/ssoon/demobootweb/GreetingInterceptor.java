package me.ssoon.demobootweb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class GreetingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) throws Exception {
        System.out.println("preHandle 1");
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler, final ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle 1");
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
        final HttpServletResponse response, final Object handler, final Exception ex)
        throws Exception {
        System.out.println("afterCompletion 1");
    }
}
