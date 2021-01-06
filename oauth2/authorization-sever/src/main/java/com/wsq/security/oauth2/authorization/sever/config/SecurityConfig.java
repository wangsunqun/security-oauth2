package com.wsq.security.oauth2.authorization.sever.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security配置
 *
 * @author wsq
 * 2021/1/5 13:43
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null || encodedPassword.length() == 0) {
                    return false;
                }

                return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()).equalsIgnoreCase(encodedPassword);
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 基于内存，此处还可以基于数据库，还可以配合上redis
        auth.inMemoryAuthentication().
                withUser("admin").roles("admin").password(passwordEncoder().encode("123456"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 开启请求认证，就是访问接口前需要登入
                .authorizeRequests()
                // 表示剩余的其他接口，需要登录才能访问，但是不受角色限制
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
