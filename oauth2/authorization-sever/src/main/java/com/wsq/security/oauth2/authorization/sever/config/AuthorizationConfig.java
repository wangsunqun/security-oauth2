package com.wsq.security.oauth2.authorization.sever.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 授权服务器配置
 * https://blog.csdn.net/silmeweed/article/details/101603227
 *
 * @author wsq
 * 2021/1/5 17:29
 */
@Configuration
@EnableAuthorizationServer //开启授权服务
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 访问安全配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

    // clientDetailsService注入，决定clientDeatils信息的处理服务。
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //client端唯一标识
                .withClient("client-id-hello")
                //客户端的密码
                .secret(passwordEncoder.encode("client-secret"))
                //授权模式标识
                .authorizedGrantTypes("authorization_code")
                //作用域，用于限制客户端与用户无法访问没有作用域的资源
                .scopes("all")
                //资源id, 可选
                .resourceIds("rs")
                //回调地址
                .redirectUris("http://www.baidu.com");
    }

    // AuthorizationServerEndpointsConfigurer：访问端点配置。tokenStroe、tokenEnhancer服务
    /**
     * 注入相关配置：
     * 1. 密码模式下配置认证管理器 AuthenticationManager
     * 2. 设置 AccessToken的存储介质tokenStore， 默认使用内存当做存储介质。
     * 3. userDetailsService注入
     */
}
