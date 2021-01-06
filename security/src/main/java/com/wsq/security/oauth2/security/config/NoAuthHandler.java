package com.wsq.security.oauth2.security.config;

import com.alibaba.fastjson.JSON;
import com.wsq.security.oauth2.security.common.enums.ReponseStatusEnum;
import com.wsq.security.oauth2.security.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权访问
 *
 * @author wsq
 * 2021/1/6 15:37
 */
@Component
public class NoAuthHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error(ReponseStatusEnum.USER_NO_ACCESS)));
    }
}
