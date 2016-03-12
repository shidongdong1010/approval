package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户
 * @author wn
 *
 */
public interface ItbUserService {

	void save(ItbUser itbUser);

	ItbUser findById(Long id);
	
	 /**
	  * 分页查询
	  * @param example
	  * @param page
	  * @return
	 */
	Page<ItbUser> findPage(ItbUser example, Pageable page);
}
