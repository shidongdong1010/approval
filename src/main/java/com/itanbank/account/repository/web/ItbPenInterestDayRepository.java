package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbPenInterestDay;

public interface ItbPenInterestDayRepository extends JpaRepository<ItbPenInterestDay,Long>,JpaSpecificationExecutor<ItbPenInterestDay>{
	ItbPenInterestDay  findByLoanContractNoAndStatus(String loanContractNo,String status); 
}
