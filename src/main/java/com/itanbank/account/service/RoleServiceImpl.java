package com.itanbank.account.service;

import com.itanbank.account.domain.app.Role;
import com.itanbank.account.repository.app.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/1.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    /**
     * 查询用记的所有角色
     * @param userId 用户ID
     * @return
     */
    public List<Role> findByUserId(Long userId){
        return roleRepository.findByUserId(userId);
    }

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
