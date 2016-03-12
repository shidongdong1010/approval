package com.itanbank.account.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.domain.account.CsProjectInfoBak;

public interface CsProjectInfoBakRepository extends JpaRepository<CsProjectInfoBak,Long>{
	
	@Transactional
	@Modifying
	@Query(value = "Insert into akaccount.cs_project_info_bak(project_id,app_id,loan_user_id,loan_user_type,loan_name,loan_mobile,loan_contract_no,loan_amount,loan_rate,"
			+ "low_bid_amount,hig_bid_amount,add_rate,return_rate,project_term,term_unit,bid_begin_time,bid_end_time,project_name,project_desc,begin_date,end_date,"
			+ "create_user_id,create_time,pay_project_id,pay_project_no,pay_amount,server_amount,pre_interest,grant_pay_amount,put_amount,put_num,can_amount,"
			+ "is_advance,advance_company_id,balance,version,is_add_amount,add_amount,batch_no,grant_server_amount) (select p.id,p.app_id,p.loan_user_id,p.loan_user_type,p.loan_name,"
			+ "p.loan_mobile,p.loan_contract_no,p.loan_amount,p.loan_rate,p.low_bid_amount,p.hig_bid_amount,p.add_rate,p.return_rate,p.project_term,p.term_unit,"
			+ "p.bid_begin_time,p.bid_end_time,p.project_name,p.project_desc,p.begin_date,p.end_date,p.create_user_id,p.create_time,pa.pay_project_id,"
			+ "pa.pay_project_no,pa.pay_amount,pa.server_amount,pa.pre_interest,pa.grant_pay_amount,pa.put_amount,pa.put_num,pa.can_amount,"
			+ "pa.is_advance,pa.advance_company_id,pa.balance,pa.version,pa.is_add_amount,pa.add_amount,?1,pa.grant_server_amount "
			+ "from akweb.itb_project_info p ,akweb.itb_project_account pa where p.id=pa.id)", nativeQuery = true)
	void backUpProjectInfo(String batchNo) throws Exception;
}
