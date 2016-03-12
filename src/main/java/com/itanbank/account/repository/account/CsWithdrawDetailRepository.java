package com.itanbank.account.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.account.CsWithdrawDetail;

public interface CsWithdrawDetailRepository extends JpaRepository<CsWithdrawDetail,Long>{

	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsWithdrawDetail> queryCsWithdrawDetailByBatchNo(String batchNo);
}
