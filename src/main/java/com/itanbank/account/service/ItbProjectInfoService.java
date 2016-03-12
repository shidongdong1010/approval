package com.itanbank.account.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.enums.TransferActionEnum;
import com.itanbank.account.domain.web.enums.UserTypeEnum;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdResult;
import com.itanbank.account.pay.model.project.ProjectTransferResult;

public interface ItbProjectInfoService {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	ItbProjectInfo findById(Long id);

	/**
	 * 根据状态查询
	 * @param status
	 * @return
	 */
	List<ItbProjectInfo> findByStatus(String status);

	/**
	 * 查询未完成的返款记录
	 * @return
	 */
	List<ItbProjectInfo> findForAdvance();

	/**
	 * 分页查询标的信息
	 * @param example
	 * @param page
	 * @return
	 */
	public Page<ItbProjectInfo> findPage(ItbProjectInfo example, Pageable page);
	
	/**
	 * 满标
	 * @param id 标的id
	 * @return
	 * @throws Exception
	 */
	JsonResult fullProject(Long id,String ip) throws Exception;

	/**
	 * 根据标的id查询对象
	 * @param id
	 * @return
	 */
	ItbProjectInfo getProjectInfoById(Long id);

	/**
	 * 保存标的信息
	 * @param itbProjectInfo
	 * @return
	 */
	ItbProjectInfo saveItbProjectInfo(ItbProjectInfo itbProjectInfo);

	/**
	 * 满标结果通知
	 * @param ptrNopwdResult
	 * @return
	 * @throws Exception
	 */
	JsonResult fullProjectResult(ProjectTransferNopwdResult ptrNopwdResult) throws Exception;

	/**
	 * 放款申请
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	JsonResult pay(Long projectId, String ip) throws Exception;

	/**
	 * 放款接口通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	JsonResult payResult(ProjectTransferResult projectTransferResult) throws Exception;
	
	/**
	 * 平台收费申请
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	JsonResult charge(Long projectId, String ip) throws Exception;

	/**
	 * 平台收费接口通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	JsonResult chargeResult(ProjectTransferResult projectTransferResult) throws Exception;

	/**
	 * 偿付
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	JsonResult advance(Long projectId, String ip) throws Exception;

	/**
	 * 偿付结果通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	JsonResult advanceResult(ProjectTransferResult projectTransferResult) throws Exception;
	
	/**
     * 创建流水记录
     * @param orderInfo 订单信息
     * @param businessId 业务id
     * @param userTypeEnum 用户类型
     * @param tradeNo 第三方流水号
     * @param actionEnum 转账方向
     * @return
     */
	ItbCapitalFlowInfo getItbCapitalFlowInfo(ItbOrderInfo orderInfo, Long businessId, UserTypeEnum userTypeEnum,
			String tradeNo, TransferActionEnum actionEnum);

	/**
	 * 贴现结果通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	JsonResult discountResult(ProjectTransferResult projectTransferResult) throws Exception;

	/**
	 * 贴现
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	JsonResult discount(Long projectId, String ip) throws Exception;

	/**
	 * 锁定标的
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	JsonResult lockProject(Long projectId) throws Exception;
	
}
