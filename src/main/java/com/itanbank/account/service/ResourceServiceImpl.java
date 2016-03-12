package com.itanbank.account.service;

import com.itanbank.account.domain.app.Resource;
import com.itanbank.account.repository.app.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/1.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * 查询所有角色
     * @return
     */
    public List<Resource> findAll(){
        return resourceRepository.findAll();
    }

    /**
     * 查询出该角色拥有的资源
     * @param roleId
     * @return
     */
    public List<Resource> findByRoleId(Long roleId){
        return resourceRepository.findByRoleId(roleId);
    }
}
