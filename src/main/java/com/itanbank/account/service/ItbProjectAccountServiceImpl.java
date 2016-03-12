package com.itanbank.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbProjectAccount;
import com.itanbank.account.repository.web.ItbProjectAccountRepository;

/**
 * 标的
 * @author wn
 *
 */
@Service
public class ItbProjectAccountServiceImpl implements ItbProjectAccountService {

	@Autowired
	private ItbProjectAccountRepository itbProjectAccountRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@Override
	public ItbProjectAccount findById(Long id){
		return itbProjectAccountRepository.findOne(id);
	}


	@Override
	public ItbProjectAccount saveProjectAccount(ItbProjectAccount itbProjectAccount) {
		return itbProjectAccountRepository.save(itbProjectAccount);
	}
	@Override
	public ItbProjectAccount getProjectAccountById(Long id){
		return itbProjectAccountRepository.findOne(id);
	}


	/**
	 * 更新标的余额
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	public boolean updateBalanceByIdAndVersion(Double amount, Long id, Long version){
		int result = itbProjectAccountRepository.updateBalanceByIdAndVersion(amount, id, version);
		if(result != 1) return false;
		return true;
	}

	/**
	 * 更新标的可投金额
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	public boolean updateCanAmountByIdAndVersion(Double amount, Long id, Long version){
		int result = itbProjectAccountRepository.updateCanAmountByIdAndVersion(amount, id, version);
		if(result != 1) return false;
		return true;
	}

	/**
	 * 更新标的余额-- 贴现业务
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	public boolean updateDiscountByIdAndVersion(Double amount, String isAddAmount, Long id, Long version) {
		int result = itbProjectAccountRepository.updateDiscountByIdAndVersion(amount, isAddAmount, id, version);
		if(result != 1) return false;
		return true;
	}

	/**
	 * 更新标的余额 -- 收取服务费业务
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	public boolean updateServerAmountByIdAndVersion(Double amount, Double serverAmount, Long id, Long version){
		int result = itbProjectAccountRepository.updateServerAmountByIdAndVersion(amount, serverAmount, id, version);
		if(result != 1) return false;
		return true;
	}

	/**
	 * 更新标的余额-满标
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	@Override
	public boolean updateFullProjectByIdAndVersion(Double amount, Long id, Long version) {
		int result = itbProjectAccountRepository.updateFullProjectByIdAndVersion(amount, id, version);
		if(result != 1) return false;
		return true;
	}
	/**
	 * 更新标的余额 --偿付
	 * @param amount
	 * @param isAdvance
	 * @param id
	 * @param version
	 * @return
	 */
	@Override
	public boolean updateAdvanceByIdAndVersion(Double amount, String isAdvance,Long id, Long version) {
		int result = itbProjectAccountRepository.updateAdvanceByIdAndVersion(amount, isAdvance,id, version);
		if(result != 1) return false;
		return true;
	}
}
