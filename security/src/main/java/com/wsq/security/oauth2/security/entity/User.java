package com.wsq.security.oauth2.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Set;

/**
 * 实现security的userDetails接口
 *
 * @author wsq
 * 2021/1/6 15:50
 */
@Data
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 7171722954972237961L;

    private Long id;
    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities;

    // 为了简化，以下直接全开
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
