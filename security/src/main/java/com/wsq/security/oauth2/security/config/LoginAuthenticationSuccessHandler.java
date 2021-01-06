package com.wsq.security.oauth2.security.config;

import com.alibaba.fastjson.JSON;
import com.wsq.security.oauth2.security.entity.User;
import com.wsq.security.oauth2.security.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登入成功
 *
 * @author wsq
 * 2021/1/6 15:41
 */
@Component
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User userDetail = (User) authentication.getPrincipal();

        String token = DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()).getBytes());

        httpServletResponse.getWriter().write(JSON.toJSONString(Result.success(token)));
    }
}
