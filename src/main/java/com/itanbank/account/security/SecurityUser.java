package com.itanbank.account.security;

import com.itanbank.account.domain.app.Role;
import com.itanbank.account.domain.app.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Security 用户实体
 * Created by dongdongshi on 16/1/12.
 */
public class SecurityUser extends User implements UserDetails {

    private static final long serialVersionUID = 1L;
    public SecurityUser(User user) {
        if(user != null) {
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setMobile(user.getMobile());
            this.setEnabled(user.getEnabled());
            this.setExpired(user.getExpired());
            this.setLocked(user.getLocked());
            this.setLastLoginTime(user.getLastLoginTime());
            this.setLoginErrorCount(user.getLoginErrorCount());
            this.setFullname(user.getFullname());
            this.setPassword(user.getPassword());
            this.setCreateTime(user.getCreateTime());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> userRoles = this.getRoles();

        if(userRoles != null && !userRoles.isEmpty()) {
            for (Role role : userRoles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getCode());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * 帐户是否失效
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return super.getEnabled().intValue() == 0;
    }

    /**
     * 帐户是否锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return super.getLocked().intValue() == 0;
    }

    /**
     * 认证未失效
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return super.getExpired().intValue() == 0;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
