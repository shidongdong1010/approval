package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbPayInfo;

/**
 * 放款记录
 * @author wn
 *
 */
public interface ItbPayService {


	/**
	 * 保存对象
	 * @param itbInvestInfo
	 * @return
	 */
	ItbPayInfo save(ItbPayInfo itbPayInfo);

}
