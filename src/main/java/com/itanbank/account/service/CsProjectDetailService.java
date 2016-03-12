package com.itanbank.account.service;

import java.util.List;

import com.itanbank.account.domain.account.CsProjectDetail;

public interface CsProjectDetailService {
	/**
	 * 保存CsProjectDetail
	 * @param csProjectDetail
	 * @return
	 */
	public CsProjectDetail saveCsProjectDetail(CsProjectDetail csProjectDetail);
	
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsProjectDetail> queryCsProjectDetailByBatchNo(String batchNo);
}
