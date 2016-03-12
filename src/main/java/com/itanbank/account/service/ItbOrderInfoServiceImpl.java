package com.itanbank.account.service;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.domain.web.enums.UserTypeEnum;
import com.itanbank.account.pay.model.pay.RechargeEnterpriseRequest;
import com.itanbank.account.pay.model.pay.WithdrawEnterpriseRequest;
import com.itanbank.account.pay.service.PayService;
import org.epbcommons.transformation.math.MathUtil;
import org.epbcommons.transformation.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbAddAmountInfo;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.enums.OrderStatusEnum;
import com.itanbank.account.pay.enums.ServTypeEnum;
import com.itanbank.account.repository.web.ItbOrderInfoRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by SDD on 2016/2/25.
 */
@Service
public class ItbOrderInfoServiceImpl implements ItbOrderInfoService {


    @Autowired
    private OrderNoService orderNoService;

    @Autowired
    private ItbOrderInfoRepository itbOrderInfoRepository;

    @Autowired
    private ItbCompanyInfoService itbCompanyInfoService;

    @Autowired
    private PayService payService;

    @Override
    public ItbOrderInfo findById(Long id){
        return itbOrderInfoRepository.findOne(id);
    }

    @Override
    public List<ItbOrderInfo> findByBusinessTypeAndOrderStatus(String businessType, String orderStatus){
        return itbOrderInfoRepository.findByBusinessTypeAndOrderStatus(businessType, orderStatus);
    }

    /**
     * 查询订单时间超过指定秒数的订单
     * @param businessType 业务类型
     * @param orderStatus 订单状态
     * @param timeout 超时秒数
     * @return
     */
    @Override
    public List<ItbOrderInfo> findTimeroutOrderByBusinessTypeAndOrderStatus(String businessType, String orderStatus, long timeout){
        return itbOrderInfoRepository.findTimeroutOrderByBusinessTypeAndOrderStatus(businessType, orderStatus, new Double(timeout));
    }

    @Override
    public ItbOrderInfo findByOrderNo(String orderNo){
        return itbOrderInfoRepository.findByOrderNo(orderNo);
    }

    @Override
    public void save(ItbOrderInfo orderInfo){
        itbOrderInfoRepository.save(orderInfo);
    }

    /**
     * 企业充值下订单
     * @param companyId 企业ID
     * @param ip
     * @param rechargeEnterpriseReq
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @Override
    public String addEnterpriseRechargeOrderInfo(Long companyId, String ip, RechargeEnterpriseRequest rechargeEnterpriseReq)  throws Exception{
        ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(companyId);
        // 请求参数金额为元
        double amount = new Double(rechargeEnterpriseReq.getAmount());
        // 将元转换为分, 第三方请求的参数为分
        rechargeEnterpriseReq.setAmount(String.valueOf(Math.round(MathUtil.mul(amount, 100d))));

        // 获得订单号
        String orderNo = orderNoService.getOrderNo(BusinessTypeEnum.ENTERPRISE_RECHARGE.getCode());

        ItbOrderInfo orderInfo = new ItbOrderInfo();
        orderInfo.setOrderNo(orderNo);	// 订单号
        orderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
        orderInfo.setBusinessType(BusinessTypeEnum.ENTERPRISE_RECHARGE.getCode()); // 业务类型为企业充值
        orderInfo.setAmount(amount); // 金额
        orderInfo.setOrderDesc(BusinessTypeEnum.ENTERPRISE_RECHARGE.getDesc());  // 订单描述
        orderInfo.setUserId(companyInfo.getId());	// 企业ID
        orderInfo.setUserType(UserTypeEnum.ENTERPRISE.getCode()); // 企业代码
        orderInfo.setUserName(companyInfo.getName()); // 企业名称
        orderInfo.setBankCode(companyInfo.getBankCode()); // 银行代码
        orderInfo.setOrderTime(new java.util.Date()); // 订单时间
        orderInfo.setInPayNo(companyInfo.getPayId()); // 用户第三方支付帐户号
        orderInfo.setUserIp(ip);    // 用户IP
        orderInfo.setCreateTime(new java.util.Date()); // 创建时间
        orderInfo.setVersion(0L); // 版本号

        itbOrderInfoRepository.save(orderInfo);


        // 设置订单日期
        rechargeEnterpriseReq.setMer_date(new SimpleDateFormat("yyyyMMdd").format(orderInfo.getOrderTime()));
        // 设置订单号
        rechargeEnterpriseReq.setOrder_id(orderInfo.getOrderNo());
        // 被充值企业资金账户托管平台商户号
        rechargeEnterpriseReq.setRecharge_mer_id(companyInfo.getPayId());
        // 发卡行编号
        rechargeEnterpriseReq.setGate_id(companyInfo.getBankCode());
        // 用户IP
        rechargeEnterpriseReq.setUser_ip(ip);

        // 获得企业客户充值申请的URL
        return payService.getRechargeEnterpriseUrl(rechargeEnterpriseReq);
    }

    /**
     * 企业标的转账订单生成(包含有密无密)
     * @param companyInfo 企业信息
     * @param businessTypeEnum 业务类型枚举
     * @param amount 金额
     * @param ip
     * @return
     */
    @Override
    public ItbOrderInfo addEnterpriseProjectTransferOrderInfo(ItbCompanyInfo companyInfo,ItbProjectInfo itbProjectInfo, BusinessTypeEnum businessTypeEnum, Double amount, String ip){
        // 获得订单号
        String orderNo = orderNoService.getOrderNo(businessTypeEnum.getCode());

        ItbOrderInfo orderInfo = new ItbOrderInfo();
        orderInfo.setOrderNo(orderNo);	// 订单号
        orderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
        orderInfo.setBusinessType(businessTypeEnum.getCode()); // 业务类型为企业充值
        orderInfo.setAmount(amount); // 金额
        orderInfo.setProjectId(itbProjectInfo.getId());//标的id
        orderInfo.setLoanContractNo(itbProjectInfo.getLoanContractNo());//申请合同号
        orderInfo.setAppId(itbProjectInfo.getAppId());//申请id
        orderInfo.setOrderDesc(businessTypeEnum.getDesc());  // 订单描述
        orderInfo.setUserId(companyInfo.getId());	// 企业ID
        orderInfo.setUserType(UserTypeEnum.ENTERPRISE.getCode()); // 企业代码
        orderInfo.setUserName(companyInfo.getName()); // 企业名称
        orderInfo.setBankCode(companyInfo.getBankCode()); // 银行代码
        orderInfo.setOrderTime(new java.util.Date()); // 订单时间
        orderInfo.setInPayNo(companyInfo.getPayId()); // 用户第三方支付帐户号
        orderInfo.setUserIp(ip);    // 用户IP
        orderInfo.setCreateTime(new java.util.Date()); // 创建时间
        orderInfo.setVersion(0L); // 版本号

        itbOrderInfoRepository.save(orderInfo);
        return orderInfo;
    }
    /**
     * 个人标的转账订单生成(包含有密无密)
     * @param itbUser 个人用户信息
     * @param businessTypeEnum 业务类型枚举
     * @param amount 金额
     * @param ip
     * @return
     */
    @Override
    public ItbOrderInfo addPersonProjectTransferOrderInfo(ItbUser itbUser,ItbProjectInfo itbProjectInfo, BusinessTypeEnum businessTypeEnum, Double amount, String ip){
        // 获得订单号
        String orderNo = orderNoService.getOrderNo(businessTypeEnum.getCode());

        ItbOrderInfo orderInfo = new ItbOrderInfo();
        orderInfo.setOrderNo(orderNo);	// 订单号
        orderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
        orderInfo.setBusinessType(businessTypeEnum.getCode()); // 业务类型为企业充值
        orderInfo.setAmount(amount); // 金额
        orderInfo.setProjectId(itbProjectInfo.getId());//标的id
        orderInfo.setLoanContractNo(itbProjectInfo.getLoanContractNo());//申请合同号
        orderInfo.setAppId(itbProjectInfo.getAppId());//申请id
        orderInfo.setOrderDesc(businessTypeEnum.getDesc());  // 订单描述
        orderInfo.setUserId(itbUser.getId());//用户id
        orderInfo.setUserType(UserTypeEnum.PERSON.getCode());
        orderInfo.setUserName(itbUser.getRealName());//用户姓名
        orderInfo.setUserMobile(itbUser.getMobile());//用户手机
        orderInfo.setBankCode(itbUser.getBankType()); // 银行代码
        orderInfo.setOrderTime(new java.util.Date()); // 订单时间
        orderInfo.setInPayNo(itbUser.getPayId()); // 用户第三方支付帐户号
        orderInfo.setUserIp(ip);    // 用户IP
        orderInfo.setCreateTime(new java.util.Date()); // 创建时间
        orderInfo.setVersion(0L); // 版本号
        itbOrderInfoRepository.save(orderInfo);
        return orderInfo;
    }
    
    /**
     *  企业提现下订单
     * @param companyId 企业信息
     * @param withdrawEnterpriseReq 金额
     * @param ip
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @Override
    public String addEnterpriseWithdrawOrderInfo(Long companyId, String ip, WithdrawEnterpriseRequest withdrawEnterpriseReq) throws Exception{
        ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(companyId);
        // 请求参数金额为元
        double amount = new Double(withdrawEnterpriseReq.getAmount());
        // 将元转换为分, 第三方请求的参数为分
        withdrawEnterpriseReq.setAmount(String.valueOf(Math.round(MathUtil.mul(amount, 100d))));

        // 获得订单号
        String orderNo = orderNoService.getOrderNo(BusinessTypeEnum.ENTERPRISE_WITHDRAW.getCode());

        ItbOrderInfo orderInfo = new ItbOrderInfo();
        orderInfo.setOrderNo(orderNo);	// 订单号
        orderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
        orderInfo.setBusinessType(BusinessTypeEnum.ENTERPRISE_WITHDRAW.getCode()); // 业务类型为企业充值
        orderInfo.setAmount(amount); // 金额
        orderInfo.setOrderDesc(BusinessTypeEnum.ENTERPRISE_WITHDRAW.getDesc());  // 订单描述
        orderInfo.setUserId(companyInfo.getId());	// 企业ID
        orderInfo.setUserType(UserTypeEnum.ENTERPRISE.getCode()); // 企业代码
        orderInfo.setUserName(companyInfo.getName()); // 企业名称
        orderInfo.setBankCode(companyInfo.getBankCode()); // 银行代码
        orderInfo.setOrderTime(new java.util.Date()); // 订单时间
        orderInfo.setInPayNo(companyInfo.getPayId()); // 用户第三方支付帐户号
        orderInfo.setUserIp(ip);    // 用户IP
        orderInfo.setCreateTime(new java.util.Date()); // 创建时间
        orderInfo.setVersion(0L); // 版本号

        itbOrderInfoRepository.save(orderInfo);


        // 设置订单日期
        withdrawEnterpriseReq.setMer_date(new SimpleDateFormat("yyyyMMdd").format(orderInfo.getOrderTime()));
        // 设置订单号
        withdrawEnterpriseReq.setOrder_id(orderInfo.getOrderNo());
        // 被提现企业资金账户托管平台商户号
        withdrawEnterpriseReq.setWithdraw_mer_id(companyInfo.getPayId());
        return payService.getWithdrawEnterpriseUrl(withdrawEnterpriseReq);
    }

    /**
     * 更新订单状态
     * @param tradeNo 第三方交易流水号
     * @param orderStatus 订单状态
     * @param id 订单ID
     * @param version 版本号
     * @return
     */
    @Override
    public boolean updateOrderByIdAndVersion(String tradeNo, String orderStatus, Long id, Long version){
        int result = itbOrderInfoRepository.updateOrderByIdAndVersion(tradeNo, orderStatus, id, version);
        if(result != 1){
            return false;
        }
        return true;
    }
    
    @Override
    public Page<ItbOrderInfo> findPage(final ItbOrderInfo example, Pageable page) {
    	return itbOrderInfoRepository.findAll(new Specification<ItbOrderInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbOrderInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> params = new ArrayList<Predicate>();
				if(!StringUtil.isEmpty(example.getOrderNo())){
					Path<Long> usrIdPath = root.get("orderNo");
					params.add(criteriaBuilder.equal(usrIdPath, example.getOrderNo()));
				}
				if(example.getUserId()!= null){
					Path<Long> usrIdPath = root.get("userId");
					params.add(criteriaBuilder.equal(usrIdPath, example.getUserId()));
				}
				if(!StringUtil.isEmpty(example.getUserType())){
					Path<Long> usrIdPath = root.get("userType");
					params.add(criteriaBuilder.equal(usrIdPath, example.getUserType()));
				}
				if(!StringUtil.isEmpty(example.getBusinessType())){
					Path<Long> usrIdPath = root.get("businessType");
					params.add(criteriaBuilder.equal(usrIdPath, example.getBusinessType()));
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
