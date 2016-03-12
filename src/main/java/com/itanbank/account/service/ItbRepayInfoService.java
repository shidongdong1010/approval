package com.itanbank.account.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.domain.web.ItbRepayInfo;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdResult;

public interface ItbRepayInfoService {

	/**
	 * 根据projectId查询
	 * @param projectId
	 * @return
	 */
	ItbRepayInfo findByProjectId(Long projectId);

	/**
	 * 分页查询还款信息
	 * @param example
	 * @param page
	 * @return
	 */
	public Page<ItbRepayInfo> findPage(ItbRepayInfo example, Pageable page);
	
	/**
	 * 根据还款表ID去第三方企业账户划款到标的账户
	 * @param id (还款表ID)
	 * @param repayAmount(还款金额)
	 * @return
	 */
	ProjectTransferNopwdResult repayInfoRequest(Long id,Double repayAmount, String ip)throws Exception;
	
	/**
	 * 第三方企业账户转账标的账户异步回调处理
	 * @param projectTransferNopwdResult
	 */
	JsonResult  repayInfoResponse(ProjectTransferNopwdResult projectTransferNopwdResult)throws Exception;

	/**
	 * 保存对象
	 * @param itbRepayInfo
	 * @return
	 */
	ItbRepayInfo save(ItbRepayInfo itbRepayInfo);
	
	/**
	 * 根据ID查询还款信息
	 * @param id
	 * @return
	 */
	ItbRepayInfo  findById(Long id);
	
	/**
	 * 根据当前时间查询出逾期的数据
	 * @return
	 */
	List<ItbRepayInfo> queryOverdueInfo();
	
	/**
	 * 根据逾期的还款信息新增逾期表数据
	 * @param itbRepayInfoList
	 */
	void  addPenInterestDay(List<ItbRepayInfo> itbRepayInfoList);

	/**
	 * 查询需要偿付的还款信息
	 * @return
	 */
	List<ItbRepayInfo> getAdvanceRepayInfos() throws Exception;
	
	
	/**
	 * 分页查询还款信息（数据管理）
	 * @param example
	 * @param page
	 * @return
	 */
	public Page<ItbRepayInfo> findQueryPage(ItbRepayInfo example, Pageable page);
}
