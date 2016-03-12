package com.itanbank.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.ItbRechargeInfo;
import com.itanbank.account.domain.web.ItbWithdrawInfo;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.pay.RechargePersonResult;
import com.itanbank.account.pay.model.pay.WithdrawPersonResult;

/**
 * 提现业务
 * Created by SDD on 2016/2/25.
 */
public interface ItbWithdrawInfoService {

    /**
     * 企业提现业务处理
     * @param withdrawPersonResult 第三方提现结果参数
     * @return
     */
    ExecuetResultCode enterpriseWithdraw(WithdrawPersonResult withdrawPersonResult) throws Exception;
    
    /**
   	 * 分页查询
   	 * @param example
   	 * @param page
   	 * @return
   	 */
   	Page<ItbWithdrawInfo> findPage(ItbWithdrawInfo example, Pageable page);
}
