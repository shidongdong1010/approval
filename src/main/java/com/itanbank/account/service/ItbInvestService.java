package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbInvestInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbRechargeInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 投资记录
 * @author wn
 *
 */
public interface ItbInvestService {

	/**
	 * 查询投资记录
	 * @param project
	 * @param userId
	 * @return
	 */
	List<ItbInvestInfo> findByProjectIdAndUserId(Long project, Long userId);

	/**
	 * 查询标的的投资记录
	 * @param projectId
	 * @return
	 */
	List<ItbInvestInfo> findByProjectId(Long projectId);

	/**
	 * 统计标的的投资记录
	 * @param projectId
	 * @param status
	 * @return
	 */
	Long countByProjectIdAndStatus(Long projectId, String status);

	/**
	 * 查询标的的投资记录
	 * @param projectId
	 * @param status
	 * @return
	 */
	List<ItbInvestInfo> findByProjectIdAndStatus(Long projectId, String status);

	/**
	 * 统计标中已经返款的投资记录数
	 * @param projectId
	 * @return
	 */
	Long countByBackUser(Long projectId);

	ItbInvestInfo findById(Long id);

	/**
	 * 保存对象
	 * @param itbInvestInfo
	 * @return
	 */
	ItbInvestInfo saveItbInvest(ItbInvestInfo itbInvestInfo);


	/**
	 * 取消超时的订单
	 * @throws Exception
	 */
	void cancelTimeoutOrder(ItbOrderInfo orderInfo) throws Exception;
	
	 
    /**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbInvestInfo> findPage(ItbInvestInfo example, Pageable page);

	/**
	 * 查询标的应发收益
	 * @param projectId
	 * @return
	 */
	Double queryProjectSumInterest(Long projectId);
}
