package com.itanbank.account.service;

import com.itanbank.account.domain.app.Resource;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/1.
 */
public interface ResourceService {

    /**
     * 查询所有资源
     * @return
     */
    public List<Resource> findAll();

    /**
     * 查询出该角色拥有的资源
     * @param roleId
     * @return
     */
    public List<Resource> findByRoleId(Long roleId);
}
