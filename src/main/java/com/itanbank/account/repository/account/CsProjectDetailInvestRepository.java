package com.itanbank.account.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.account.CsProjectDetailInvest;

public interface CsProjectDetailInvestRepository extends JpaRepository<CsProjectDetailInvest,Long>{
	/**
	 * 
	 * @param projectDetailId
	 * @return
	 */
	List<CsProjectDetailInvest> queryCsProjectDetailInvestByProjectDetailId(Long projectDetailId);
}
