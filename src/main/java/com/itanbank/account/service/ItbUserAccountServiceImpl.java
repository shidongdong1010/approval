package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.ItbUserAccount;
import com.itanbank.account.repository.web.ItbUserAccountRepository;
import com.itanbank.account.repository.web.ItbUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItbUserAccountServiceImpl implements ItbUserAccountService{

	@Autowired
	private ItbUserAccountRepository itbUserAccountRepository;

	@Override
	public void save(ItbUserAccount itbUserAccount){
		itbUserAccountRepository.save(itbUserAccount);
	}

	@Override
	public ItbUserAccount findById(Long id){
		return itbUserAccountRepository.findOne(id);
	}

	/**
	 * 更新标的余额
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	@Override
	public boolean updateBalanceByIdAndVersion(Double amount, Long id, Long version){
		int result = itbUserAccountRepository.updateBalanceByIdAndVersion(amount, id, version);
		if(result != 1) return false;
		return true;
	}
}
