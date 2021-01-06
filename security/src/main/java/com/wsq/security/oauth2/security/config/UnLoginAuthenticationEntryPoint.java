package com.wsq.security.oauth2.security.config;

import com.alibaba.fastjson.JSON;
import com.wsq.security.oauth2.security.common.enums.ReponseStatusEnum;
import com.wsq.security.oauth2.security.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录时返回给前端的数据
 *
 * @author wsq
 * 2021/1/6 15:30
 */

@Component
public class UnLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error(ReponseStatusEnum.AUTH_ERROR)));
    }
}
