package com.itanbank.account.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.domain.account.CsCompanyAccountBak;

public interface CsCompanyAccountBakRepository extends JpaRepository<CsCompanyAccountBak,Long>{
	
	@Transactional
	@Modifying
	@Query(value = "insert into akaccount.cs_company_account_bak(company_id,code,name,bank_code,bank_no,pay_id,pay_no,create_time,status,type,balance,version,batch_no)"
			+ "(select c.id,c.code,c.name,c.bank_code,c.bank_no,c.pay_id,c.pay_no,c.create_time,c.status,c.type,c.balance,c.version,?1 from akweb.itb_company_info c)", nativeQuery = true)
	void backUpCompanyInfo(String batchNo) throws Exception;
}
