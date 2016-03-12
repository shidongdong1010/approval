package com.itanbank.account.service;

import java.util.List;

import com.itanbank.account.domain.account.CsWithdrawDetail;

public interface CsWithdrawDetailService {
	/**
	 * 保存
	 * @param csWithdrawDetail
	 */
	public void saveCsWithdrawDetail(CsWithdrawDetail csWithdrawDetail);
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsWithdrawDetail> queryCsWithdrawDetailByBatchNo(String batchNo);
}
