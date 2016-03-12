package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import com.itanbank.account.domain.web.ItbRechargeInfo;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.pay.RechargePersonResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 充值业务
 * Created by SDD on 2016/2/25.
 */
public interface ItbRechargeInfoService {

    /**
     * 企业充值业务处理
     * @param rechargePersonResult 第三方充值结果参数
     * @return
     */
    ExecuetResultCode enterpriseRecharge(RechargePersonResult rechargePersonResult) throws Exception;

    /**
     * 处理结果不明的的充值订单-处理单条-企业充值
     * @param rechargeInfo
     * @throws Exception
     */
    void handleUnkonwnRechargeInfoCompany(ItbRechargeInfo rechargeInfo) throws Exception;

    /**
     * 处理结果不明的的充值订单-处理单条-个人充值
     * @param rechargeInfo
     * @throws Exception
     */
    void handleUnkonwnRechargeInfoPerson(ItbRechargeInfo rechargeInfo) throws Exception;
    
    /**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbRechargeInfo> findPage(ItbRechargeInfo example, Pageable page);
}
