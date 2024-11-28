package com.test.swagger.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import java.util.Objects;

@Component
public class MyAppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws AuthenticationException {
        String authorization = request.getHeader("Authorization");

            if(authorization.equals("test")) {
                return true;
            }

        throw new RuntimeException("Authorization Token Error");
    }
}
