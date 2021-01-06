package com.wsq.security.oauth2.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.util.DigestUtils;

/**
 * 资源服务器配置
 *
 * @author wsq
 * 2021/1/5 19:37
 */
@EnableResourceServer
@Configuration
public class ResourceConfig extends ResourceServerConfigurerAdapter {
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

    @Primary
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        //设置授权服务器check_token端点完整地址
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        //设置客户端id与secret，注意：client_secret值不能使用passwordEncoder加密！
        tokenServices.setClientId("client-id-hello");
        tokenServices.setClientSecret("client-secret");
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("rs").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        // 所有请求都要认证才可以访问
        http.authorizeRequests().anyRequest().authenticated();
    }
}
