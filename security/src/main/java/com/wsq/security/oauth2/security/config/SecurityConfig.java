package com.wsq.security.oauth2.security.config;

import com.wsq.security.oauth2.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO
 *
 * @author wsq
 * 2021/1/5 13:43
 */
@Order(1)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 未登陆时返回 JSON 格式的数据给前端
    @Autowired
    private UnLoginAuthenticationEntryPoint entryPoint;

    // 登录成功返回的 JSON 格式数据给前端
    @Autowired
    private LoginAuthenticationSuccessHandler loginSuccessHandler;

    // 登录失败返回的 JSON 格式数据给前端
    @Autowired
    private LoginAuthenticationFailureHandler loginFailureHandler;

    // 注销成功返回的 JSON 格式数据给前端
    @Autowired
    private LogoutAuthenticationSuccessHandler logoutSuccessHandler;

    // 无权访问返回的 JSON 格式数据给前端
    @Autowired
    private NoAuthHandler noAuthHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

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
        // 基于内存
//        auth.inMemoryAuthentication().
//                withUser("admin").roles("admin").password("e10adc3949ba59abbe56e057f20f883e");

        // 基于数据库
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用session，提升性能
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // 配置entry point
                .httpBasic().authenticationEntryPoint(entryPoint).and()

                // 开启请求认证
                .authorizeRequests()
                // 有多种匹配方式，ant是根据uri匹配
                // 表示只有访问 /hello下的接口需要登入
                .antMatchers("/hello/**").authenticated().and()

                // 表示访问 /hello2 这个接口，需要具备 admin 这个角色
                // .antMatchers("/hello2").hasRole("admin")
                // 表示剩余的其他接口，需要登录才能访问，但是不受角色限制
                //.anyRequest().authenticated()

                // 开启表单登入
                .formLogin()
                // 自定义登入页面，默认/login
                //.loginPage("/login_p")
                // 校验用户名密码路径, 也就是form表单提交时指定的action，默认跟loginPage一样
                // 后面的原理其实就是注册一个filter 然后根据传进去的uri进行拦截
                //.loginProcessingUrl("/oauth/login")

                // 登录成功的处理器
                .successHandler(loginSuccessHandler)
                //登录失败的处理器
                .failureHandler(loginFailureHandler)
                // 和表单登录相关的接口统统都直接通过
                .permitAll().and()

                // 登出处理
                .logout()
                // 登出逻辑处理地址，默认/logout
                //.logoutUrl("/oauth/logout")
                // 登出成功处理器
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and()
                .csrf().disable();

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(myUserDetailsService).tokenValiditySeconds(1000);

        // 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(noAuthHandler);


        http.addFilterBefore(, UsernamePasswordAuthenticationFilter.class);
    }
}
