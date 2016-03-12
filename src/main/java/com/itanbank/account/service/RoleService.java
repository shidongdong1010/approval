package com.itanbank.account.service;

import com.itanbank.account.domain.app.Role;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/1.
 */
public interface RoleService {

    /**
     * 查询用户的所有角色
     * @param userId 用户ID
     * @return
     */
    public List<Role> findByUserId(Long userId);

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAll();
}
