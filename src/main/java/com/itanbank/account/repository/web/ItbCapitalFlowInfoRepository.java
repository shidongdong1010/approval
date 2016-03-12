package com.itanbank.account.repository.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itanbank.account.domain.web.ItbCapitalFlowInfo;

/**
 * Created by SDD on 2016/2/25.
 */
public interface ItbCapitalFlowInfoRepository extends JpaRepository<ItbCapitalFlowInfo,Long>,JpaSpecificationExecutor<ItbCapitalFlowInfo> {

	ItbCapitalFlowInfo findByOrderNo(String orderNo);
	
	/**
	 * 得到已还金额
	 * @param businessType
	 * @param endtime
	 * @param repayStatus
	 * @return
	 */
	@Query(nativeQuery = true,value="select f.business_id repayId,SUM(f.amount)amount from itb_capital_flow_info f where f.business_type=?1 and TIMESTAMPDIFF(SECOND,?2,f.time)<=0 and EXISTS(select * from itb_repay_info t where t.status=?3 and t.id = f.business_id) group by f.business_id")
	public List<Object> getPaidAmount(String businessType,String endtime,String repayStatus);
}
