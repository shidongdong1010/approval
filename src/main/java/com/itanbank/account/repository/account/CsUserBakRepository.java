package com.itanbank.account.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.domain.account.CsUserBak;

public interface CsUserBakRepository extends JpaRepository<CsUserBak,Long>{
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO akaccount.cs_user_bak(uid,mobile,password,real_name,id_card,is_real_name_auth,bank_type,bank_no,bank_desc,is_bank_bind,"
			+ "is_quick_pay,pay_id,pay_no,is_pay_no,reg_source,enabled,expired,locked,head_path,last_login_time,login_error_count,create_time,total_amount,"
			+ "used_amount,invest_amount,gold,point,version,bacth_no) "
			+ "(select u.id,u.mobile,u.password,u.real_name,u.id_card,u.is_real_name_auth,u.bank_type,u.bank_no,u.bank_desc,u.is_bank_bind,u.is_quick_pay,"
			+ "u.pay_id,u.pay_no,u.is_pay_no,u.reg_source,u.enabled,u.expired,u.locked,u.head_path,u.last_login_time,u.login_error_count,u.create_time,"
			+ "ua.total_amount,ua.used_amount,ua.invest_amount,ua.gold,ua.point,ua.version,?1  from akweb.itb_user u,akweb.itb_user_account ua where u.id=ua.id)", nativeQuery = true)
	void backUpUserInfo(String batchNo) throws Exception;
}
