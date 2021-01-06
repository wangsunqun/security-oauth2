package com.wsq.security.oauth2.security.service;

import com.wsq.security.oauth2.security.dao.UserMapper;
import com.wsq.security.oauth2.security.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取用户信息
 *
 * @author wsq
 * 2021/1/6 17:18
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByName(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        Set<GrantedAuthority> authoritiesSet = new HashSet<>();
        // 模拟从数据库中获取用户角色
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        authoritiesSet.add(authority);

        user.setAuthorities(authoritiesSet);

        log.info("用户{}验证通过",username);
        return user;
    }
}
