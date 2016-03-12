package com.itanbank.account.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.account.CsProjectTransferDetail;

public interface CsProjectTransferDetailRepository extends JpaRepository<CsProjectTransferDetail,Long>{
	
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsProjectTransferDetail> queryCsProjectTransferDetailByBatchNo(String batchNo);
}
