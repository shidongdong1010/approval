package com.itanbank.account.service;

import java.util.Map;

import com.itanbank.account.domain.app.User;
import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 资金流水
 * @author wn
 *
 */
public interface ItbCapitalFlowService {

	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbCapitalFlowInfo> findPage(ItbCapitalFlowInfo example, Pageable page);

	/**
	 * 保存对象
	 * @param capitalFlowInfo
	 * @return
	 */
	ItbCapitalFlowInfo saveItbCapitalFlow(ItbCapitalFlowInfo capitalFlowInfo);

	/**
	 * 得到已还金额
	 * @param businessType 业务类型
	 * @param endtime 截止时间
	 * @param repayStatus 还款状态
	 * @return
	 */
	Map<Long, Double>  getPaidAmount(String businessType, String endtime, String repayStatus);
	
	/**
	 * 根据订单号查询流水信息
	 * @param orderNo
	 * @return
	 */
	ItbCapitalFlowInfo  findByOrderNo(String orderNo);

}
