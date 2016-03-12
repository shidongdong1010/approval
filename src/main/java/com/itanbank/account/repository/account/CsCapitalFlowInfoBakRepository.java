package com.itanbank.account.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.domain.account.CsCapitalFlowInfoBak;

public interface CsCapitalFlowInfoBakRepository extends JpaRepository<CsCapitalFlowInfoBak,Long>{
	
	@Transactional
	@Modifying
	@Query(value = "insert into akaccount.cs_capital_flow_info_bak(capital_id,business_id,business_type,order_no,trade_no,amount,time,user_id,user_type,"
			+ "trans_action,project_id,create_time,batch_no)(select c.id,c.business_id,c.business_type,c.order_no,c.trade_no,c.amount,c.time,c.user_id,"
			+ "c.user_type,c.trans_action,c.project_id,c.create_time,?1 from akweb.itb_capital_flow_info c where c.create_time like ?2)", nativeQuery = true)
	void backUpCapitalInfo(String batchNo,String date) throws Exception;
}
