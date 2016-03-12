package com.itanbank.account.service;

import java.util.List;

import com.itanbank.account.domain.account.CsRechargeDetail;

public interface CsRechargeDetailService {

	/**
	 * 保存CsRechargeDetail
	 * @param csRechargeDetail
	 */
	public void saveCsRechargeDetail(CsRechargeDetail csRechargeDetail); 
	
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsRechargeDetail> queryCsRechargeDetailByBatchNo(String batchNo);
}
