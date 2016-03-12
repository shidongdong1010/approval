package com.itanbank.account.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.account.CsRechargeDetail;

public interface CsRechargeDetailRepository extends JpaRepository<CsRechargeDetail,Long>{
	
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	List<CsRechargeDetail> queryCsRechargeDetailByBatchNo(String batchNo);
}
