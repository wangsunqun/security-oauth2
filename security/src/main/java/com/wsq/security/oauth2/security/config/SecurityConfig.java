package com.wsq.security.oauth2.security.config;

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
                withUser("admin").roles("admin").password("e10adc3949ba59abbe56e057f20f883e");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 开启请求认证，就是访问接口前需要登入
                .authorizeRequests()
                // 有多种匹配方式，ant是根据uri匹配
                // 表示访问 /hello下所有接口都不需要登入
                .antMatchers("/hello/**").authenticated()
                //.antMatchers("/hello/**").authenticated()
                // 表示访问 /hello2 这个接口，需要具备 admin 这个角色
//                .antMatchers("/hello2").hasRole("admin")
//                // 表示剩余的其他接口，需要登录才能访问，但是不受角色限制
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 自定义登入页面，默认/login
                //.loginPage("/login_p")
                // 定义登录时，用户名的 key，默认为 username
                //.usernameParameter("uname")
                // 定义登录时，用户密码的 key，默认为 password
                //.passwordParameter("passwd")
                // 校验用户名密码路径, 也就是form表单提交时指定的action，默认跟loginPage一样
                // 后面的原理其实就是注册一个filter 然后根据传进去的uri进行拦截
                .loginProcessingUrl("/oauth/login")
                //登录成功的处理器
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        Authentication authentication) throws IOException, ServletException {

                        // 利用前端传cookie等方式得到之前的重定向地址，比如一开始访问 /hello2，但是登入时候地址被
                        // 重定向为/login，所以需要前端再把/hello2传回来，后端再次重定向到接口
                        System.out.println("successHandler");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
                        //
                        System.out.println("failureHandler");
                    }
                })
                // 和表单登录相关的接口统统都直接通过
                .permitAll()
                .and()
                .logout()
                // 登出逻辑处理地址，默认/logout
                //.logoutUrl("/oauth/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        //
                        System.out.println("logoutSuccessHandler");
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }
}
