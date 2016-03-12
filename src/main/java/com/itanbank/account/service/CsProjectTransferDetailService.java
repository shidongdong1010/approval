package com.itanbank.account.service;

import java.util.List;

import com.itanbank.account.domain.account.CsProjectTransferDetail;

public interface CsProjectTransferDetailService {
	
	/**
	 * 保存
	 * @param csProjectTransferDetail
	 */
	public void saveCsProjectTransferDetail(CsProjectTransferDetail csProjectTransferDetail);

	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsProjectTransferDetail> queryCsProjectTransferDetailByBatchNo(String batchNo);
}
