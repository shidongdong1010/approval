package com.itanbank.account.service;

import java.util.List;

import com.itanbank.account.domain.account.CsUserAccountDetail;

public interface CsUserAccountDetailService {
	
	/**
	 * 保存
	 * @param csUserAccountDetail
	 */
	public void saveCsUserAccountDetail(CsUserAccountDetail csUserAccountDetail);
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsUserAccountDetail> queryCsUserAccountDetailByBatchNo(String batchNo);
}
