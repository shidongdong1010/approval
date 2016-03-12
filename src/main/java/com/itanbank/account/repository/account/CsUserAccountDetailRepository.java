package com.itanbank.account.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.account.CsUserAccountDetail;

public interface CsUserAccountDetailRepository extends JpaRepository<CsUserAccountDetail,Long>{

	/**
	 * 根据批次查询
	 * @param batchNo
	 * @return
	 */
	public List<CsUserAccountDetail> queryCsUserAccountDetailByBatchNo(String batchNo);
}
