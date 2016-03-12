package com.itanbank.account.security;

import com.itanbank.account.common.AuthUtil;
import com.itanbank.account.domain.app.Role;
import com.itanbank.account.domain.app.User;
import com.itanbank.account.service.RoleService;
import com.itanbank.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dongdongshi on 16/1/12.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        User user = userService.findUserByUsername(username);
        if (user == null) {
            // throw new UsernameNotFoundException(messageHelper.getMessage("AbstractUserDetailsAuthenticationProvider.UsernameNotFound"));
            throw new UsernameNotFoundException("AbstractUserDetailsAuthenticationProvider.UsernameNotFound");
        }
        // 获得用户拥有的角色
        user.setRoles(this.getRole(user.getId()));
        // SecurityUser实现UserDetails并将SUser的Email映射为username
        return new SecurityUser(user); //code9
    }

    /**
     * 根据用户获取该用户拥有的角色
     * @param userId
     * @return
     */
    private Set<Role> getRole(Long userId) {
        Set<Role> roles = new HashSet<Role>();
        roles.addAll(roleService.findByUserId(userId));
        // 添加登陆的角色
        Role roleLogin = new Role();
        roleLogin.setCode(AuthUtil.ROLE_LOGIN.getAuthority());
        roles.add(roleLogin);

        return roles;
    }
}
