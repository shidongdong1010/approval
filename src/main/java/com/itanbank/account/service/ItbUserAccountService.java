package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.ItbUserAccount;

/**
 * 用户账户
 * @author wn
 *
 */
public interface ItbUserAccountService {

	void save(ItbUserAccount itbUserAccount);

	ItbUserAccount findById(Long id);

	/**
	 * 更新用户账户余额
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	boolean updateBalanceByIdAndVersion(Double amount, Long id, Long version);
}
