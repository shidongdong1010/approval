package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.pay.model.pay.RechargeEnterpriseRequest;
import com.itanbank.account.pay.model.pay.WithdrawEnterpriseRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单
 * Created by SDD on 2016/2/25.
 */
@Service
public interface ItbOrderInfoService {

    ItbOrderInfo findById(Long id);

    List<ItbOrderInfo> findByBusinessTypeAndOrderStatus(String businessType, String orderStatus);

    /**
     * 查询订单时间超过指定秒数的订单
     * @param businessType 业务类型
     * @param orderStatus 订单状态
     * @param timeout 超时秒数
     * @return
     */
    List<ItbOrderInfo> findTimeroutOrderByBusinessTypeAndOrderStatus(String businessType, String orderStatus, long timeout);


    ItbOrderInfo findByOrderNo(String orderNo);

    void save(ItbOrderInfo orderInfo);

    /**
     * 企业充值下订单
     * @param companyId 企业ID
     * @param ip
     * @param rechargeEnterpriseReq
     * @return
     */
    String addEnterpriseRechargeOrderInfo(Long companyId, String ip, RechargeEnterpriseRequest rechargeEnterpriseReq)  throws Exception;

    /**
     *  企业提现下订单
     * @param companyId 企业信息
     * @param withdrawEnterpriseReq 金额
     * @param ip
     * @return
     */
    String addEnterpriseWithdrawOrderInfo(Long companyId, String ip, WithdrawEnterpriseRequest withdrawEnterpriseReq) throws Exception;

    /**
     * 更新订单状态
     * @param tradeNo 第三方交易流水号
     * @param orderStatus 订单状态
     * @param id 订单ID
     * @param version 版本号
     * @return
     */
    boolean updateOrderByIdAndVersion(String tradeNo, String orderStatus, Long id, Long version);

    /**
     * 企业标的转账订单生成(包含有密无密)
     * @param companyInfo 企业信息
     * @param businessTypeEnum 业务类型枚举
     * @param amount 金额
     * @param ip
     * @return
     */
	ItbOrderInfo addEnterpriseProjectTransferOrderInfo(ItbCompanyInfo companyInfo,ItbProjectInfo itbProjectInfo, BusinessTypeEnum businessTypeEnum,
			Double amount, String ip);

	/**
     * 个人标的转账订单生成(包含有密无密)
     * @param itbUser 个人用户信息
     * @param businessTypeEnum 业务类型枚举
     * @param amount 金额
     * @param ip
     * @return
     */
	ItbOrderInfo addPersonProjectTransferOrderInfo(ItbUser itbUser, ItbProjectInfo itbProjectInfo,
                                                   BusinessTypeEnum businessTypeEnum, Double amount, String ip);
	
	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbOrderInfo> findPage(ItbOrderInfo example, Pageable page);
}
