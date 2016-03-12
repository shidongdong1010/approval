package com.itanbank.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.VItbProjectAll;

/**
 * Created by dongdongshi on 16/2/1.
 */
public interface VItbProjectAllService {

    /**
     * 查询标的和标的账户表
     * @param id 用户ID
     * @return
     */
    public VItbProjectAll findById(Long id);


    public Page<VItbProjectAll> findPage(final VItbProjectAll example, Pageable page) ;
}
