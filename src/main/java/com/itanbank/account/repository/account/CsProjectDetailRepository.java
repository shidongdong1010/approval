package com.itanbank.account.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.account.CsProjectDetail;

public interface CsProjectDetailRepository extends JpaRepository<CsProjectDetail,Long>{
	
	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	List<CsProjectDetail> queryCsProjectDetailByBatchNo(String batchNo);
}
