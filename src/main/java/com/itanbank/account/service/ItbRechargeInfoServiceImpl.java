package com.itanbank.account.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.itanbank.account.domain.web.*;
import com.itanbank.account.domain.web.enums.LoanUserTypeEnum;
import com.itanbank.account.domain.web.enums.TransferActionEnum;

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

import com.itanbank.account.domain.web.enums.OrderStatusEnum;
import com.itanbank.account.domain.web.enums.RechargeStatusEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.TransferRechargeEnum;
import com.itanbank.account.pay.model.pay.RechargePersonResult;
import com.itanbank.account.pay.model.query.TransferRequest;
import com.itanbank.account.pay.model.query.TransferResult;
import com.itanbank.account.pay.service.QueryTransFerService;
import com.itanbank.account.repository.web.ItbCapitalFlowInfoRepository;
import com.itanbank.account.repository.web.ItbRechargeInfoRepository;

/**
 * Created by SDD on 2016/2/25.
 */
@Transactional
@Service
public class ItbRechargeInfoServiceImpl implements ItbRechargeInfoService{

    @Autowired
    private ItbOrderInfoService itbOrderInfoService;
    @Autowired
    private ItbRechargeInfoRepository itbRechargeInfoRepository;
    @Autowired
    private ItbCapitalFlowInfoRepository itbCapitalFlowInfoRepository;
    @Autowired
    private ItbCompanyInfoService itbCompanyInfoService;
    @Autowired
    private ItbUserAccountService itbUserAccountService;

    @Autowired
    private QueryTransFerService  queryTransFerService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 企业充值业务处理
     * @param rechargePersonResult 第三方充值结果参数
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public ExecuetResultCode enterpriseRecharge(RechargePersonResult rechargePersonResult) throws Exception{
        String orderNo = rechargePersonResult.getOrder_id();  // 订单号
        String tradeNo = rechargePersonResult.getTrade_no();  // 第三方交易流水号

        // 查询订单信息
        ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
        // 查询企业账户
        ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());

        if(orderInfo == null){
            logger.error("充值业务处理失败, 未查询到该订单。 订单号：{}", orderNo);
            return ExecuetResultCode.E60503;
        }
        // 验证订单状态是否为支付中
        if(OrderStatusEnum.PAYMENT.equals(orderInfo.getOrderStatus())){
            logger.error("充值业务处理失败, 订单目前状态不是支付中。 订单信息：{}", orderInfo);
            return ExecuetResultCode.E60781;
        }

        // 处理返回结果
        ExecuetResultCode resultCode = ExecuetResultCode.getByCode(rechargePersonResult.getRet_code());
        if(ExecuetResultCode.SCUESS.equals(resultCode)){ // 支付成功
            this.enterpriseRechargeSuccess(orderInfo, tradeNo, companyInfo, rechargePersonResult.getRet_msg());
        }else if (ExecuetResultCode.MIDDLE_STATE.equals(resultCode)){ // 结果不明
            this.enterpriseRechargeMiddleState(orderInfo, tradeNo, rechargePersonResult.getRet_msg());
        }else { // 支付失败
            this.enterpriseRechargeFailure(orderInfo, tradeNo, rechargePersonResult.getRet_msg());
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
    private void enterpriseRechargeSuccess(ItbOrderInfo orderInfo, String tradeNo, ItbCompanyInfo companyInfo, String retMsg) throws Exception{
        // 得到充值记录
        ItbRechargeInfo rechargeInfo =getItbRechargeInfo(orderInfo);
        // 充值记录状态为成功
        rechargeInfo.setStatus(RechargeStatusEnum.SUCCESS.getCode());
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 得到流水记录
        ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, rechargeInfo, tradeNo);
        itbCapitalFlowInfoRepository.save(capitalFlowInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果成功，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果成功，更新订单表失败。 订单信息：" + orderInfo);
        }
        // 修改企业账户
        isPass = itbCompanyInfoService.updateBalanceByIdAndVersion(orderInfo.getAmount(), companyInfo.getId(), companyInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果成功，更新企业账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果成功，更新企业账户表失败。 订单信息：" + orderInfo);
        }
        logger.info("支付结果为：{}。 业务处理完成。订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 支付结果为结果不明 处理
     * @param orderInfo
     * @param tradeNo
     * @param retMsg
     * @throws Exception
     */
    private void enterpriseRechargeMiddleState(ItbOrderInfo orderInfo, String tradeNo, String retMsg) throws Exception{
        // 得到充值记录
        ItbRechargeInfo rechargeInfo =getItbRechargeInfo(orderInfo);
        // 充值记录状态为结果不明
        rechargeInfo.setStatus(RechargeStatusEnum.MIDDLE_STATE.getCode());
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 得到流水记录
        //ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, rechargeInfo, tradeNo);
        //itbCapitalFlowInfoRepository.save(capitalFlowInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果不明，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果不明，更新订单表失败。 订单信息：" + orderInfo);
        }

        // 修改企业账户
       /* ItbCompanyInfo companyInfo = itbCompanyInfoRepository.findOne(orderInfo.getCompanyId());
        int result = itbCompanyInfoRepository.updateBalanceByIdAndVersion(orderInfo.getAmount(), orderInfo.getId(), orderInfo.getVersion());
        if(result != 1){
            logger.error("充值业务处理失败，更新企业账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("充值业务处理失败，更新企业账户表失败。 订单信息：" + orderInfo);
        }*/

        logger.info("支付结果为：{}。 订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 支付失败处理
     * @param orderInfo
     * @param tradeNo
     * @param retMsg
     * @throws Exception
     */
    private void enterpriseRechargeFailure(ItbOrderInfo orderInfo, String tradeNo, String retMsg) throws Exception{
        // 得到充值记录
        ItbRechargeInfo rechargeInfo =getItbRechargeInfo(orderInfo);
        // 充值记录状态为失败
        rechargeInfo.setStatus(RechargeStatusEnum.FAILURE.getCode());
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果失败，更新订单表失败。 订单信息：" + orderInfo);
        }
        logger.info("提现通知结果失败，业务处理完成。 失败原因：{}， 订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 创建充值记录
     * @param orderInfo 订单信息
     * @return
     */
    private ItbRechargeInfo getItbRechargeInfo(ItbOrderInfo orderInfo){
        ItbRechargeInfo rechargeInfo = new ItbRechargeInfo();
        rechargeInfo.setCompanyId(orderInfo.getUserId());
        rechargeInfo.setOrderNo(orderInfo.getOrderNo());
        rechargeInfo.setAmount(orderInfo.getAmount());
        rechargeInfo.setCreateTime(new java.util.Date());
        return rechargeInfo;
    }

    /**
     * 创建流水记录
     * @param orderInfo 订单信息
     * @param rechargeInfo 充值记录
     * @param tradeNo 第三方流水号
     * @return
     */
    private ItbCapitalFlowInfo getItbCapitalFlowInfo(ItbOrderInfo orderInfo, ItbRechargeInfo rechargeInfo, String tradeNo){
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
     * 处理结果不明的的充值订单-处理单条
     * @param rechargeInfo
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void handleUnkonwnRechargeInfoCompany(ItbRechargeInfo rechargeInfo) throws Exception{
        ItbOrderInfo  orderInfo = itbOrderInfoService.findByOrderNo(rechargeInfo.getOrderNo());
        ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(rechargeInfo.getCompanyId());

        // 从第三方查询交易状态
        TransferRequest  transferRequest = new TransferRequest();
        transferRequest.setOrder_id(rechargeInfo.getOrderNo());
        transferRequest.setMer_date(new SimpleDateFormat("yyyyMMdd").format(orderInfo.getOrderTime()));
        transferRequest.setBusi_type(TransferRequest.BUSI_TYPE_RECHARGE);
        TransferResult transferResult = queryTransFerService.transfer(transferRequest);

        // 处理业务逻辑
        if(ExecuetResultCode.SCUESS.getCode().equals(transferResult.getRet_code())){
            //查询成功
            if(TransferRechargeEnum.TRANSFER_RRCHARGE_OK.getCode().equals(transferResult.getTran_state())){
                // 充值记录状态为成功
                rechargeInfo.setStatus(RechargeStatusEnum.SUCCESS.getCode());
                //充值成功
                this.enterpriseRechargeSuccess(orderInfo, rechargeInfo, orderInfo.getTradeNo(), companyInfo, transferResult.getRet_msg());
            }else if(TransferRechargeEnum.TRANSFER_RRCHARGE_UNKNOWN.getCode().equals(transferResult.getTran_state())){
                // 结果不明,不处理
                logger.info("结果不明,不处理{}", orderInfo);
                // this.enterpriseRechargeMiddleState(orderInfo, orderInfo.getTradeNo(), transferResult.getRet_msg());
            }else{
                // 充值记录状态为失败
                rechargeInfo.setStatus(RechargeStatusEnum.FAILURE.getCode());
                this.enterpriseRechargeFailure(orderInfo, rechargeInfo, orderInfo.getTradeNo(), transferResult.getRet_msg());
            }
        }
    }

    /**
     * 支付成功处理
     * @param orderInfo
     * @param tradeNo
     * @param companyInfo
     * @throws Exception
     */
    private void enterpriseRechargeSuccess(ItbOrderInfo orderInfo, ItbRechargeInfo rechargeInfo, String tradeNo, ItbCompanyInfo companyInfo, String retMsg) throws Exception{
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 得到流水记录
        ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, rechargeInfo, tradeNo);
        itbCapitalFlowInfoRepository.save(capitalFlowInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果成功，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果成功，更新订单表失败。 订单信息：" + orderInfo);
        }
        // 修改企业账户
        isPass = itbCompanyInfoService.updateBalanceByIdAndVersion(orderInfo.getAmount(), companyInfo.getId(), companyInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果成功，更新企业账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果成功，更新企业账户表失败。 订单信息：" + orderInfo);
        }
        logger.info("支付结果为：{}。 业务处理完成。订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 支付失败处理
     * @param orderInfo
     * @param tradeNo
     * @param retMsg
     * @throws Exception
     */
    private void enterpriseRechargeFailure(ItbOrderInfo orderInfo, ItbRechargeInfo rechargeInfo, String tradeNo, String retMsg) throws Exception{
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果失败，更新订单表失败。 订单信息：" + orderInfo);
        }
        logger.info("提现通知结果失败，业务处理完成。 失败原因：{}， 订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 处理结果不明的的充值订单-处理单条
     * @param rechargeInfo
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void handleUnkonwnRechargeInfoPerson(ItbRechargeInfo rechargeInfo) throws Exception{
        ItbOrderInfo  orderInfo = itbOrderInfoService.findByOrderNo(rechargeInfo.getOrderNo());
        ItbUserAccount userAccount = itbUserAccountService.findById(rechargeInfo.getUserId());

        // 从第三方查询交易状态
        TransferRequest  transferRequest = new TransferRequest();
        transferRequest.setOrder_id(rechargeInfo.getOrderNo());
        transferRequest.setMer_date(new SimpleDateFormat("yyyyMMdd").format(orderInfo.getOrderTime()));
        transferRequest.setBusi_type(TransferRequest.BUSI_TYPE_RECHARGE);
        TransferResult transferResult = queryTransFerService.transfer(transferRequest);

        // 处理业务逻辑
        if(ExecuetResultCode.SCUESS.getCode().equals(transferResult.getRet_code())){
            //查询成功
            if(TransferRechargeEnum.TRANSFER_RRCHARGE_OK.getCode().equals(transferResult.getTran_state())){
                // 充值记录状态为成功
                rechargeInfo.setStatus(RechargeStatusEnum.SUCCESS.getCode());
                //充值成功
                this.personRechargeSuccess(orderInfo, rechargeInfo, orderInfo.getTradeNo(), userAccount, transferResult.getRet_msg());
            }else if(TransferRechargeEnum.TRANSFER_RRCHARGE_UNKNOWN.getCode().equals(transferResult.getTran_state())){
                // 结果不明,不处理
                logger.info("结果不明,不处理{}", orderInfo);
                // this.enterpriseRechargeMiddleState(orderInfo, orderInfo.getTradeNo(), transferResult.getRet_msg());
            }else{
                // 充值记录状态为失败
                rechargeInfo.setStatus(RechargeStatusEnum.FAILURE.getCode());
                this.personRechargeFailure(orderInfo, rechargeInfo, orderInfo.getTradeNo(), transferResult.getRet_msg());
            }
        }
    }

    /**
     * 个人充值支付成功处理
     * @param orderInfo
     * @param tradeNo
     * @param userAccount
     * @throws Exception
     */
    private void personRechargeSuccess(ItbOrderInfo orderInfo, ItbRechargeInfo rechargeInfo, String tradeNo, ItbUserAccount userAccount, String retMsg) throws Exception{
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 得到流水记录
        ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, rechargeInfo, tradeNo);
        itbCapitalFlowInfoRepository.save(capitalFlowInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果成功，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果成功，更新订单表失败。 订单信息：" + orderInfo);
        }
        // 修改企业账户
        isPass = itbUserAccountService.updateBalanceByIdAndVersion(orderInfo.getAmount(), userAccount.getId(), userAccount.getVersion());
        if(!isPass){
            logger.error("提现通知结果成功，更新用户账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果成功，更新用户账户表失败。 订单信息：" + orderInfo);
        }
        logger.info("支付结果为：{}。 业务处理完成。订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 个人充值支付失败处理
     * @param orderInfo
     * @param tradeNo
     * @param retMsg
     * @throws Exception
     */
    private void personRechargeFailure(ItbOrderInfo orderInfo, ItbRechargeInfo rechargeInfo, String tradeNo, String retMsg) throws Exception{
        // 保存充值充值记录
        itbRechargeInfoRepository.save(rechargeInfo);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("提现通知结果失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("提现通知结果失败，更新订单表失败。 订单信息：" + orderInfo);
        }
        logger.info("提现通知结果失败，业务处理完成。 失败原因：{}， 订单信息：{}", retMsg, orderInfo);
    }
    
    /**
   	 * 分页查询
   	 * @param example
   	 * @param page
   	 * @return
   	 */
    @Override
    public Page<ItbRechargeInfo> findPage(final ItbRechargeInfo example, Pageable page) {
    	return itbRechargeInfoRepository.findAll(new Specification<ItbRechargeInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbRechargeInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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
