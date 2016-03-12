package com.itanbank.account.service;

import com.itanbank.account.domain.web.*;
import com.itanbank.account.domain.web.enums.*;
import com.itanbank.account.domain.web.enums.WithdrawStatusEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.WithdrawTradeStateEnum;
import com.itanbank.account.pay.model.pay.WithdrawPersonResult;
import com.itanbank.account.pay.model.pay.WithdrawPersonResult;
import com.itanbank.account.repository.web.ItbCapitalFlowInfoRepository;
import com.itanbank.account.repository.web.ItbCompanyInfoRepository;
import com.itanbank.account.repository.web.ItbOrderInfoRepository;
import com.itanbank.account.repository.web.ItbWithdrawInfoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by SDD on 2016/2/25.
 */
@Transactional
@Service
public class ItbWithdrawInfoServiceImpl implements ItbWithdrawInfoService{

    @Autowired
    private ItbOrderInfoService itbOrderInfoService;
    @Autowired
    private ItbWithdrawInfoRepository itbWithdrawInfoRepository;
    @Autowired
    private ItbCapitalFlowInfoRepository itbCapitalFlowInfoRepository;
    @Autowired
    private ItbCompanyInfoService itbCompanyInfoService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 企业提现业务处理
     * @param withdrawPersonResult 第三方提现结果参数
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public ExecuetResultCode enterpriseWithdraw(WithdrawPersonResult withdrawPersonResult) throws Exception{
        String orderNo = withdrawPersonResult.getOrder_id();  // 订单号
        String tradeNo = withdrawPersonResult.getTrade_no();  // 第三方交易流水号

        // 查询订单信息
        ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
        // 查询企业账户
        ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());

        if(orderInfo == null){
            logger.error("提现业务处理失败, 未查询到该订单。 订单号：{}", orderNo);
            return ExecuetResultCode.E60503;
        }
        // 验证订单状态是否为支付中
        if(OrderStatusEnum.PAYMENT.equals(orderInfo.getOrderStatus())){
            logger.error("提现业务处理失败, 订单目前状态不是支付中。 订单信息：{}", orderInfo);
            return ExecuetResultCode.E60781;
        }

        // 处理返回结果
        ExecuetResultCode resultCode = ExecuetResultCode.getByCode(withdrawPersonResult.getRet_code());  // 返回码
        WithdrawTradeStateEnum withdrawTradeState = WithdrawTradeStateEnum.getByCode(withdrawPersonResult.getTrade_state()); // 交易状态

        if(ExecuetResultCode.SCUESS.equals(resultCode) && WithdrawTradeStateEnum.SUCCESS.equals(withdrawTradeState)){ // 交易成功
            this.enterpriseWithdrawSuccess(orderInfo, tradeNo, companyInfo);
        }else if(WithdrawTradeStateEnum.FAIL.equals(withdrawTradeState)){ // 交易失败
            this.enterpriseWithdrawFailure(orderInfo, tradeNo, withdrawPersonResult);
        }
        return ExecuetResultCode.SCUESS;
    }

    /**
     * 支付成功处理
     * @param orderInfo
     * @param tradeNo
     * @param companyInfo
     * @throws Exception
     */
    private void enterpriseWithdrawSuccess(ItbOrderInfo orderInfo, String tradeNo, ItbCompanyInfo companyInfo) throws Exception{
        // 得到提现记录
        ItbWithdrawInfo rechargeInfo = getItbWithdrawInfo(orderInfo);
        // 提现记录状态为成功
        rechargeInfo.setStatus(WithdrawStatusEnum.SUCCESS.getCode());
        // 保存提现提现记录
        itbWithdrawInfoRepository.save(rechargeInfo);

        // 得到流水记录
        ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, rechargeInfo, tradeNo);
        itbCapitalFlowInfoRepository.save(capitalFlowInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现业务处理失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现业务处理失败，更新订单表失败。 订单信息：" + orderInfo);
        }
        // 修改企业账户
        isPass = itbCompanyInfoService.updateBalanceByIdAndVersion(-orderInfo.getAmount(), companyInfo.getId(), companyInfo.getVersion());
        if(!isPass){
            logger.error("提现业务处理失败，更新企业账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现业务处理失败，更新企业账户表失败。 订单信息：" + orderInfo);
        }
        logger.info("提现业务处理成功。 业务处理完成。 订单信息：{}", orderInfo);
    }

    /**
     * 支付失败处理
     * @param orderInfo
     * @param tradeNo
     * @throws Exception
     */
    private void enterpriseWithdrawFailure(ItbOrderInfo orderInfo, String tradeNo, WithdrawPersonResult withdrawPersonResult) throws Exception{
        // 得到提现记录
        ItbWithdrawInfo rechargeInfo =getItbWithdrawInfo(orderInfo);
        // 提现记录状态为成功
        rechargeInfo.setStatus(WithdrawStatusEnum.FAILURE.getCode());
        // 保存提现提现记录
        itbWithdrawInfoRepository.save(rechargeInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现业务处理失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现业务处理失败，更新订单表失败。 订单信息：" + orderInfo);
        }

        logger.info("提现业务处理失败。 失败原因：{}， 订单信息：{}", withdrawPersonResult.getRet_msg(), orderInfo);
    }

    /**
     * 创建提现记录
     * @param orderInfo 订单信息
     * @return
     */
    private ItbWithdrawInfo getItbWithdrawInfo(ItbOrderInfo orderInfo){
        ItbWithdrawInfo withdrawInfo = new ItbWithdrawInfo();
        withdrawInfo.setCompanyId(orderInfo.getUserId());
        withdrawInfo.setOrderNo(orderInfo.getOrderNo());
        withdrawInfo.setAmount(orderInfo.getAmount());
        withdrawInfo.setCreateTime(new java.util.Date());
        return withdrawInfo;
    }

    /**
     * 创建流水记录
     * @param orderInfo 订单信息
     * @param rechargeInfo 提现记录
     * @param tradeNo 第三方流水号
     * @return
     */
    private ItbCapitalFlowInfo getItbCapitalFlowInfo(ItbOrderInfo orderInfo, ItbWithdrawInfo rechargeInfo, String tradeNo){
        ItbCapitalFlowInfo capitalFlowInfo = new ItbCapitalFlowInfo();
        capitalFlowInfo.setBusinessId(rechargeInfo.getId());
        capitalFlowInfo.setBusinessType(orderInfo.getBusinessType());
        capitalFlowInfo.setOrderNo(orderInfo.getOrderNo());
        capitalFlowInfo.setTradeNo(tradeNo);
        capitalFlowInfo.setUserId(orderInfo.getUserId());
        capitalFlowInfo.setUserType(orderInfo.getUserType());
        capitalFlowInfo.setAmount(orderInfo.getAmount());
        capitalFlowInfo.setTime(orderInfo.getOrderTime());
        capitalFlowInfo.setCreateTime(new java.util.Date());
        return capitalFlowInfo;
    }
    
    
    /**
   	 * 分页查询
   	 * @param example
   	 * @param page
   	 * @return
   	 */
    @Override
    public Page<ItbWithdrawInfo> findPage(final ItbWithdrawInfo example, Pageable page) {
    	return itbWithdrawInfoRepository.findAll(new Specification<ItbWithdrawInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbWithdrawInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(example.getOrderNo())){
					Path<String> orderNoPath = root.get("orderNo");
					params.add(criteriaBuilder.equal(orderNoPath, example.getOrderNo()));
				}
				if(example.getUserId() != null){
					Path<Long> usrIdPath = root.get("userId");
					params.add(criteriaBuilder.equal(usrIdPath, example.getUserId()));
				}
				if(example.getCompanyId() != null){
					Path<Long> usrIdPath = root.get("companyId");
					params.add(criteriaBuilder.equal(usrIdPath, example.getCompanyId()));
				}
				Predicate[] predicates = new Predicate[params.size()];
				criteriaQuery.where(params.toArray(predicates));

                Path<String> createTimePath = root.get("createTime");
                criteriaQuery.orderBy(criteriaBuilder.asc(createTimePath));
				return null;
			}
		}, page);
    }
}
