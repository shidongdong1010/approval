package com.itanbank.account.service;

import com.itanbank.account.domain.app.Role;
import com.itanbank.account.domain.web.VItbUserAll;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/1.
 */
public interface VItbUserAllService {

    /**
     * 查询用户和账户信息
     * @param id 用户ID
     * @return
     */
    public VItbUserAll findById(Long id);
}
