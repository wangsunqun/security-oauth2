package com.wsq.security.oauth2.security.config;

import com.alibaba.fastjson.JSON;
import com.wsq.security.oauth2.security.common.enums.ReponseStatusEnum;
import com.wsq.security.oauth2.security.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登入失败
 *
 * @author wsq
 * 2021/1/6 15:41
 */
@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error(ReponseStatusEnum.LOGIN_ERROR)));
    }
}
