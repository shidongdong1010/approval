package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbProjectAccount;

/**
 * 标的账户
 * @author wn
 *
 */
public interface ItbProjectAccountService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ItbProjectAccount findById(Long id);

    /**
     * 保存标的账户
     * @param itbProjectAccount
     * @return
     */
    ItbProjectAccount saveProjectAccount(ItbProjectAccount itbProjectAccount);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
	ItbProjectAccount getProjectAccountById(Long id);

    /**
     * 更新标的余额
     * @param amount
     * @param id
     * @param version
     * @return
     */
    boolean updateBalanceByIdAndVersion(Double amount, Long id, Long version);

    /**
     * 更新标的可投金额
     * @param amount
     * @param id
     * @param version
     * @return
     */
    boolean updateCanAmountByIdAndVersion(Double amount, Long id, Long version);

    /**
     * 更新标的余额 -- 贴现业务
     * @param amount
     * @param id
     * @param version
     * @return
     */
    boolean updateDiscountByIdAndVersion(Double amount, String isAddAmount, Long id, Long version);

    /**
     * 更新标的余额 -- 收取服务费业务
     * @param amount
     * @param id
     * @param version
     * @return
     */
    boolean updateServerAmountByIdAndVersion(Double amount, Double serverAmount, Long id, Long version);

    /**
	 * 更新标的余额-满标
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	boolean updateFullProjectByIdAndVersion(Double amount, Long id, Long version);

	/**
	 * 更新标的余额 --偿付
	 * @param amount
	 * @param isAdvance
	 * @param id
	 * @param version
	 * @return
	 */
	boolean updateAdvanceByIdAndVersion(Double amount, String isAdvance, Long id, Long version);
}
    
