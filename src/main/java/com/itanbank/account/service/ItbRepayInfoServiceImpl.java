package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.itanbank.account.domain.web.enums.*;
import org.apache.commons.lang3.StringUtils;
import org.epbcommons.transformation.amount.AmountUtils;
import org.epbcommons.transformation.date.DateUtil;
import org.epbcommons.transformation.math.MathUtil;
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

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.domain.app.enums.YesOrNoEnum;
import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbPenInterestDay;
import com.itanbank.account.domain.web.ItbProjectAccount;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;
import com.itanbank.account.domain.web.ItbServerRateConfig;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.ProjectTransferNopwdAccType;
import com.itanbank.account.pay.enums.ProjectTransferNopwdParticType;
import com.itanbank.account.pay.enums.ProjectTransferNopwdSevType;
import com.itanbank.account.pay.enums.ServTypeEnum;
import com.itanbank.account.pay.enums.TransActionEnum;
import com.itanbank.account.pay.model.BasicModel;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdRequest;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdResult;
import com.itanbank.account.pay.service.ProjectService;
import com.itanbank.account.repository.web.ItbCapitalFlowInfoRepository;
import com.itanbank.account.repository.web.ItbCompanyInfoRepository;
import com.itanbank.account.repository.web.ItbOrderInfoRepository;
import com.itanbank.account.repository.web.ItbPenInterestDayRepository;
import com.itanbank.account.repository.web.ItbProjectAccountRepository;
import com.itanbank.account.repository.web.ItbProjectInfoRepository;
import com.itanbank.account.repository.web.ItbRepayInfoRepository;
import com.itanbank.account.repository.web.ItbServerRateConfigRepository;

@Service
public class ItbRepayInfoServiceImpl implements ItbRepayInfoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItbRepayInfoRepository itbRepayInfoRepository;

	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;

	@Autowired
	private ItbProjectInfoRepository itbProjectInfoRepository;

	@Autowired
	private ItbProjectAccountService itbProjectAccountService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ItbOrderInfoService itbOrderInfoService;

	@Autowired
	private OrderNoService orderNoService;

	@Autowired
	private ItbCapitalFlowInfoRepository itbCapitalFlowInfoRepository;
	
	@Autowired
	private ItbPenInterestDayRepository itbPenInterestDayRepository;
	
	@Autowired
	private ItbServerRateConfigRepository  itbServerRateConfigRepository;
	/**
	 * 根据projectId查询
	 * 
	 * @param projectId
	 * @return
	 */
	@Override
	public ItbRepayInfo findByProjectId(Long projectId) {
		return itbRepayInfoRepository.findByProjectId(projectId);
	}

	@Override
	public Page<ItbRepayInfo> findPage(final ItbRepayInfo example, Pageable page) {
		return itbRepayInfoRepository.findAll(new Specification<ItbRepayInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbRepayInfo> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
					List<Predicate> params = new ArrayList<Predicate>();
				if (StringUtils.isNoneBlank(example.getLoanContractNo())) {
					Path<String> loanContractNoPath = root.get("loanContractNo");
					params.add(criteriaBuilder.equal(loanContractNoPath, example.getLoanContractNo()));
				}
				Path<String> statusPath =  root.get("status");
				params.add(criteriaBuilder.notEqual(statusPath, RepayStatusEnum.REPAY_OK.getCode()));

				Predicate[] predicates = new Predicate[params.size()];
				criteriaQuery.where(params.toArray(predicates));
				//criteriaQuery.where(root.get("status").)
				//criteriaQuery.where(root.get("status").in(RepayStatusEnum.REPAYING.getCode(),RepayStatusEnum.REPAY_OVERDUE.getCode()));
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("duetoRepayDate")));
				return null;
			}
		}, page);
	}

	/**
	 * 根据还款表ID去第三方企业账户划款到标的账户
	 * @param id(还款表ID)
	 * @param repayAmount(还款金额)
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public ProjectTransferNopwdResult repayInfoRequest(Long id, Double repayAmount, String ip) throws Exception {
		logger.info("还款管理 --根据信息，调用第三方接口，进行借款人托管账户划款到标的账户操作，还款ID：" + id);
		ProjectTransferNopwdResult projectTransferNopwdResult = new ProjectTransferNopwdResult();
		if (null != id) {
			ItbRepayInfo itbRepayInfo = itbRepayInfoRepository.findOne(id);
			if (null != itbRepayInfo) {
				// 企业账户信息
				ItbCompanyInfo itbCompanyInfo = itbCompanyInfoService.findById(itbRepayInfo.getLoanUserId());
				// 标的账户信息
				ItbProjectAccount ItbProjectAccount = itbProjectAccountService.findById(itbRepayInfo.getProjectId());
				// 组装第三方请求模板
				String orderNo = orderNoService.getOrderNo(ServTypeEnum.REPAY.getCode());

				// 说明请求成功，新增订单表
				ItbOrderInfo itbOrderInfo = new ItbOrderInfo();
				itbOrderInfo.setOrderNo(orderNo); // 订单号
				itbOrderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
				itbOrderInfo.setBusinessType(ServTypeEnum.REPAY.getCode()); // 业务类型为企业充值
				itbOrderInfo.setAmount(repayAmount); // 金额
				itbOrderInfo.setOrderDesc(ServTypeEnum.REPAY.getDesc()); // 订单描述
				itbOrderInfo.setUserId(itbCompanyInfo.getId()); // 企业ID
				itbOrderInfo.setUserType(UserTypeEnum.ENTERPRISE.getCode()); // 企业代码
				itbOrderInfo.setUserName(itbCompanyInfo.getName()); // 企业名称
				itbOrderInfo.setBankCode(itbCompanyInfo.getBankCode()); // 银行代码
				itbOrderInfo.setOrderTime(new java.util.Date()); // 订单时间
				itbOrderInfo.setOutPayNo(itbCompanyInfo.getId().toString()); // 用户第三方支付帐户号
				itbOrderInfo.setUserIp(ip); // 用户IP
				itbOrderInfo.setCreateTime(new java.util.Date()); // 创建时间
				itbOrderInfo.setVersion(0L); // 版本号
				itbOrderInfo.setTradeNo(projectTransferNopwdResult.getTrade_no());// 交易流水
				itbOrderInfo.setProjectId(ItbProjectAccount.getId());// 标的ID
				itbOrderInfoService.save(itbOrderInfo);// 保存订单信息

				ProjectTransferNopwdRequest projectTransferNopwdRequest = new ProjectTransferNopwdRequest();
				projectTransferNopwdRequest.setProject_id(ItbProjectAccount.getPayProjectId().toString());// 标的ID
				projectTransferNopwdRequest.setOrder_id(orderNo);// 订单号
				//projectTransferNopwdRequest.setProject_account_id(ItbProjectAccount.getPayProjectNo().toString());// 标的账户号
				projectTransferNopwdRequest.setServ_type(ProjectTransferNopwdSevType.REPAY.getCode());// 业务类型还款
				projectTransferNopwdRequest.setPartic_type(ProjectTransferNopwdParticType.REPAY.getCode());
				;
				/** 转账方账户类型01对私（个人用户）02对公（企业用户） */
				projectTransferNopwdRequest.setPartic_acc_type(ProjectTransferNopwdAccType.ENTERPRISE.getCode());
				projectTransferNopwdRequest.setPartic_user_id(itbCompanyInfo.getPayId());
				;
				//projectTransferNopwdRequest.setPartic_account_id(itbCompanyInfo.getPayNo());
				projectTransferNopwdRequest.setAmount(AmountUtils.changeY2F(repayAmount.toString()));// 还款金额，
																										// 元转分
				projectTransferNopwdRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/project/transferNotify/rePayMent.html");
				projectTransferNopwdResult = projectService.transferNopwd(projectTransferNopwdRequest);
				if (ExecuetResultCode.SCUESS.getCode().equals(projectTransferNopwdResult.getRet_code())) {
					// 更新还款表信息状态，为中间状态
					itbRepayInfo.setStatus(RepayStatusEnum.REPAY_HANDLE.getCode());
					itbRepayInfoRepository.save(itbRepayInfo);
				}
			}
		}
		return projectTransferNopwdResult;
	}

	/**
	 * 第三方企业账户转账标的账户异步回调处理
	 * 
	 * @param projectTransferNopwdResult
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult repayInfoResponse(ProjectTransferNopwdResult projectTransferNopwdResult)throws Exception {
		logger.info("企业还款标的账户第三方回调通知开始，回调参数："+projectTransferNopwdResult.toString());
		if (null != projectTransferNopwdResult) {
			// 根据返回订单号查询订单信息
			ItbOrderInfo itbOrderInfo = itbOrderInfoService.findByOrderNo(projectTransferNopwdResult.getOrder_id());
			// 交易流水号
			String tradeNo = projectTransferNopwdResult.getTrade_no();
			if (OrderStatusEnum.PAYMENT.getCode().equals(itbOrderInfo.getOrderStatus())) {
				// 說明該訂單狀態正在支付中
				if (ExecuetResultCode.SCUESS.getCode().equals(projectTransferNopwdResult.getRet_code())) {
					// 标的信息
					ItbProjectInfo itbProjectInfo = itbProjectInfoRepository.findOne(itbOrderInfo.getProjectId());
					// 标的账务信息
					ItbProjectAccount itbProjectAccount = itbProjectAccountService.findById(itbOrderInfo.getProjectId());
					// 企业账户信息
					ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(itbOrderInfo.getUserId());
					
					// 查询还款表信息
					ItbRepayInfo itbRepayInfo = itbRepayInfoRepository.findByProjectId(itbOrderInfo.getProjectId());
					
					// 罚息表信息
					ItbPenInterestDay itbPenInterestDay = itbPenInterestDayRepository.findByLoanContractNoAndStatus(itbRepayInfo.getLoanContractNo(),PenInterestDayStatusEnum.NORMAL.getCode());
					
					// 生成转入资金流水
					ItbCapitalFlowInfo itbCapitalFlowInfoChangeInto = new ItbCapitalFlowInfo();
					itbCapitalFlowInfoChangeInto.setBusinessId(itbRepayInfo.getId());
					itbCapitalFlowInfoChangeInto.setBusinessType(BusinessTypeEnum.REPAY.getCode());
					itbCapitalFlowInfoChangeInto.setAmount(itbOrderInfo.getAmount());
					itbCapitalFlowInfoChangeInto.setProjectId(itbOrderInfo.getProjectId());
					itbCapitalFlowInfoChangeInto.setOrderNo(itbOrderInfo.getOrderNo());
					itbCapitalFlowInfoChangeInto.setUserId(itbOrderInfo.getUserId());
					itbCapitalFlowInfoChangeInto.setUserType(itbOrderInfo.getUserType());
					itbCapitalFlowInfoChangeInto.setTradeNo(projectTransferNopwdResult.getTrade_no());
					itbCapitalFlowInfoChangeInto.setTransAction(TransActionEnum.IN.getCode());
					itbCapitalFlowInfoChangeInto.setCreateTime(new Date());
					itbCapitalFlowInfoChangeInto.setTime(new Date());
					itbCapitalFlowInfoRepository.save(itbCapitalFlowInfoChangeInto);

					itbRepayInfo.setRepayTime(new Date());//更新还款时间
					// 对还款进行拆分
					this.repaySplit(itbRepayInfo, itbOrderInfo.getAmount());

					Double sumDuetoAmount = MathUtil.sumDouble(MathUtil.sumDouble(itbRepayInfo.getDuetoReapyCapital()== null ?0.0:itbRepayInfo.getDuetoReapyCapital(),itbRepayInfo.getDuetoReapyInterest()==null?0.0:itbRepayInfo.getDuetoReapyInterest()), MathUtil.sumDouble(itbRepayInfo.getDuetoReapyPenalty()==null?0.0:itbRepayInfo.getDuetoReapyPenalty(),itbRepayInfo.getDuetoLateRepayServerAmount()==null?0.0:itbRepayInfo.getDuetoLateRepayServerAmount()));
					Double sumPaidAmount = MathUtil.sumDouble(MathUtil.sumDouble(itbRepayInfo.getPaidReapyCapital()==null?0.0:itbRepayInfo.getPaidReapyCapital(),itbRepayInfo.getPaidReapyInterest()==null?0.0:itbRepayInfo.getPaidReapyInterest()),MathUtil.sumDouble(itbRepayInfo.getPaidReapyPenalty()==null?0.0:itbRepayInfo.getPaidReapyPenalty(),itbRepayInfo.getPaidLateRepayServerAmount()==null?0.0:itbRepayInfo.getPaidLateRepayServerAmount()));
					if (MathUtil.equals(sumDuetoAmount, sumPaidAmount)) {
						// 应收和已还相同， 还款完成
						itbRepayInfo.setStatus(RepayStatusEnum.REPAY_OK.getCode());//还款表状态更新为垫付后已还

						if(null != itbPenInterestDay){
							//说明有逾期表信息，更新为已结清
							itbPenInterestDay.setStatus(PenInterestDayStatusEnum.CLOSED.getCode());
							itbPenInterestDayRepository.save(itbPenInterestDay);

							// 更新标的状态为返款中
							itbProjectInfo.setStatus(ProjectStatusEnum.BACK.getCode());
						} else {
							// 修改标的状态
							if(Double.parseDouble(itbProjectAccount.getIsAddAmount()) > 0){  // 需要贴现
								// 更新标的状态为贴现
								itbProjectInfo.setStatus(ProjectStatusEnum.DISCOUNTING.getCode());
							} else {
								// 更新标的状态为返款中
								itbProjectInfo.setStatus(ProjectStatusEnum.BACK.getCode());
							}
						}
						logger.info("企业还款标的账户第三方回调通知开始,还款成功,订单信息：{}", itbOrderInfo);
					}

					itbRepayInfoRepository.save(itbRepayInfo);//更新还款表
					itbProjectInfoRepository.save(itbProjectInfo);//更新标的表

					boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, OrderStatusEnum.COMPLETE_PAYMENT.getCode(), itbOrderInfo.getId(), itbOrderInfo.getVersion());
					if(!isPass){
						logger.error("企业还款业务处理失败，更新订单表失败。 订单信息：{}", itbOrderInfo);
						throw new Exception("企业还款业务处理失败，更新订单表失败。 订单信息：" + itbOrderInfo);
					}
					isPass = itbProjectAccountService.updateBalanceByIdAndVersion(itbOrderInfo.getAmount(), itbProjectAccount.getId(), itbProjectAccount.getVersion());//更新标的账户表信息
					if(!isPass){
						logger.error("企业还款业务处理失败, 更新标的账户表失败。标的账户：{}", itbProjectAccount);
						throw new Exception("企业还款业务处理失败, 更新标的账户表失败。标的账户：" + itbProjectAccount);
					}
					isPass =itbCompanyInfoService.updateBalanceByIdAndVersion(-itbOrderInfo.getAmount(), companyInfo.getId(), companyInfo.getVersion());//更新企业账户信息
					if(!isPass){
						logger.error("企业还款业务处理失败, 更新企业账户表失败。企业账户：{}", companyInfo);
						throw new Exception("企业还款业务处理失败, 更新企业账户表失败。企业账户：" + companyInfo);
					}
				} else {// 支付失败
					// 更新订单表
					boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, OrderStatusEnum.FAILURE_TO_PAY.getCode(), itbOrderInfo.getId(), itbOrderInfo.getVersion());
					if(!isPass){
						logger.error("企业还款业务处理失败，更新订单表失败。 订单信息：{}", itbOrderInfo);
						throw new Exception("企业还款业务处理失败，更新订单表失败。 订单信息：" + itbOrderInfo);
					}
				}
			}
		}
		return new JsonResult(Integer.parseInt(projectTransferNopwdResult.getRet_code()),projectTransferNopwdResult.getRet_msg());
	}

	/**
	 * 还款拆分
	 * @param itbRepayInfo
	 * @param amount
	 */
	private void repaySplit(ItbRepayInfo itbRepayInfo, double amount){
		// 还剩还款金额
		double subCapital = MathUtil.sub(itbRepayInfo.getDuetoReapyCapital(), itbRepayInfo.getPaidReapyCapital());
		double subInterest = MathUtil.sub(itbRepayInfo.getDuetoReapyInterest(), itbRepayInfo.getPaidReapyInterest());
		double subPenalty = MathUtil.sub(itbRepayInfo.getDuetoReapyPenalty(), itbRepayInfo.getPaidReapyPenalty());
		double subLateRepayServerAmount = MathUtil.sub(itbRepayInfo.getDuetoLateRepayServerAmount(), itbRepayInfo.getPaidLateRepayServerAmount());

		// 还本金
		if(amount > subCapital){
			itbRepayInfo.setPaidReapyCapital(MathUtil.add(itbRepayInfo.getPaidReapyCapital(), subCapital));
			amount = MathUtil.sub(amount, subCapital);
		} else {
			itbRepayInfo.setPaidReapyCapital(MathUtil.add(itbRepayInfo.getPaidReapyCapital(), amount));
			amount = 0d;
		}

		// 还利息
		if(amount > subInterest){
			itbRepayInfo.setPaidReapyInterest(MathUtil.add(itbRepayInfo.getPaidReapyInterest(), subInterest));
			amount = MathUtil.sub(amount, subInterest);
		} else {
			itbRepayInfo.setPaidReapyInterest(MathUtil.add(itbRepayInfo.getPaidReapyInterest(), amount));
			amount = 0d;
		}

		// 还违约金
		if(amount > subPenalty){
			itbRepayInfo.setPaidReapyPenalty(MathUtil.add(itbRepayInfo.getPaidReapyPenalty(), subPenalty));
			amount = MathUtil.sub(amount, subPenalty);
		} else {
			itbRepayInfo.setPaidReapyPenalty(MathUtil.add(itbRepayInfo.getPaidReapyPenalty(), amount));
			amount = 0d;
		}

		// 还延迟还款服务费
		if(amount > subLateRepayServerAmount){
			itbRepayInfo.setPaidLateRepayServerAmount(MathUtil.add(itbRepayInfo.getPaidLateRepayServerAmount(), subLateRepayServerAmount));
			amount = MathUtil.sub(amount, subLateRepayServerAmount);
		} else {
			itbRepayInfo.setPaidLateRepayServerAmount(MathUtil.add(itbRepayInfo.getPaidLateRepayServerAmount(), amount));
			amount = 0d;
		}
	}

	@Override
	public ItbRepayInfo save(ItbRepayInfo itbRepayInfo) {
		return itbRepayInfoRepository.save(itbRepayInfo);
	}
	
	@Override
	public ItbRepayInfo findById(Long id) {
		return itbRepayInfoRepository.findOne(id);
	}
	
	@Override
	public List<ItbRepayInfo> queryOverdueInfo() {
		return itbRepayInfoRepository.queryOverdueInfo();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public void addPenInterestDay(List<ItbRepayInfo> itbRepayInfoList) {
		logger.info("定时任务新增逾期表信息，还款表逾期数据条数："+itbRepayInfoList.size());
		if(itbRepayInfoList.size()>0){
			  //查询出配置表信息
			  ItbServerRateConfig itbServerRateConfig = itbServerRateConfigRepository.queryConfigInfo();
			  //说明当前有逾期数据
			for(ItbRepayInfo repayInfo :itbRepayInfoList){
				ItbPenInterestDay itbPenInterestDay = itbPenInterestDayRepository.findByLoanContractNoAndStatus(repayInfo.getLoanContractNo(),PenInterestDayStatusEnum.NORMAL.getCode());
				//逾期天数
				Long  deliQuencyDay = Long.parseLong(String.valueOf(DateUtil.getIntervalDays(new Date(),repayInfo.getDuetoRepayDate())));
				//未还金额
				Double repayAmount = MathUtil.sumDouble(MathUtil.sub(repayInfo.getDuetoReapyCapital()==null?0.0:repayInfo.getDuetoReapyCapital(), repayInfo.getPaidReapyCapital()==null?0.0:repayInfo.getPaidReapyCapital()), MathUtil.sub(repayInfo.getDuetoReapyInterest()==null?0.0:repayInfo.getDuetoReapyInterest(),repayInfo.getPaidReapyInterest()==null?0.0:repayInfo.getPaidReapyInterest()));
				//查询出标的信息
				ItbProjectInfo itbProjectInfo = itbProjectInfoRepository.findOne(repayInfo.getProjectId());
				if(ProjectStatusEnum.REPAY.getCode().equals(itbProjectInfo.getStatus())){
					//标的状态为还款中的， 更改为逾期中
					itbProjectInfo.setStatus(ProjectStatusEnum.LATEREPAY.getCode());//逾期中
					itbProjectInfo.setIsOverdue(YesOrNoEnum.Y.getCode()+"");
					itbProjectInfoRepository.save(itbProjectInfo);
				}
				if(null != itbPenInterestDay){
					//说明已经有存在相同合同编号的逾期数据
					itbPenInterestDay.setDeliquencyDay(deliQuencyDay);//逾期天数
					//延迟还款服务费
					itbPenInterestDay.setLateRepayServerAmount(MathUtil.mul(repayAmount, itbServerRateConfig.getLateRepayRate()==null?0.0:(itbServerRateConfig.getLateRepayRate()/100))*deliQuencyDay);
					//违约费
					itbPenInterestDay.setPenaltyAmount(MathUtil.mul(repayAmount, itbServerRateConfig.getPenaltyRate()==null?0.0:(itbServerRateConfig.getPenaltyRate()/100))*deliQuencyDay);
					itbPenInterestDayRepository.save(itbPenInterestDay);
				}else{
					//说明不存在相同合同编号的逾期数据
					itbPenInterestDay = new ItbPenInterestDay();
					itbPenInterestDay.setProjectId(repayInfo.getId());
					itbPenInterestDay.setLoanUserId(repayInfo.getLoanUserId());
					itbPenInterestDay.setLoanContractNo(repayInfo.getLoanContractNo());
					itbPenInterestDay.setCreateTime(new Date());
					itbPenInterestDay.setNoRepayCapital(MathUtil.sub(repayInfo.getDuetoReapyCapital()==null?0.0:repayInfo.getDuetoReapyCapital(), repayInfo.getPaidReapyCapital()==null?0.0:repayInfo.getPaidReapyCapital()));//未还本金
					itbPenInterestDay.setNoRepayInterest(MathUtil.sub(repayInfo.getDuetoReapyInterest()==null?0.0:repayInfo.getDuetoReapyInterest(),repayInfo.getPaidReapyInterest()==null?0.0:repayInfo.getPaidReapyInterest()));//未还服务费
					itbPenInterestDay.setNoRepayAmount(repayAmount);//未还的金额
					itbPenInterestDay.setDeliquencyDay(deliQuencyDay);//逾期天数
					//计算延迟还款服务费，和违约金(未还金额*逾期天数*利率)
					itbPenInterestDay.setLateRepayServerAmount(MathUtil.mul(repayAmount, itbServerRateConfig.getLateRepayRate()==null?0.0:(itbServerRateConfig.getLateRepayRate()/100))*deliQuencyDay);
					itbPenInterestDay.setPenaltyAmount(MathUtil.mul(repayAmount, itbServerRateConfig.getPenaltyRate()==null?0.0:(itbServerRateConfig.getPenaltyRate()/100))*deliQuencyDay);
					itbPenInterestDay.setStatus(PenInterestDayStatusEnum.NORMAL.getCode());//正常状态
					itbPenInterestDayRepository.save(itbPenInterestDay);
				}

				// 更新还款表数据
				repayInfo.setStatus(RepayStatusEnum.REPAY_OVERDUE.getCode());//更新为逾期
				repayInfo.setDuetoReapyPenalty(itbPenInterestDay.getPenaltyAmount());
				repayInfo.setDuetoLateRepayServerAmount(itbPenInterestDay.getLateRepayServerAmount());
				itbRepayInfoRepository.save(repayInfo);
			}
		}
	}
	/**
	 * 查询需要偿付的还款信息
	 * @return
	 */
	@Override
	public List<ItbRepayInfo> getAdvanceRepayInfos() throws Exception{
		return itbRepayInfoRepository.getAdvanceRepayInfos(RepayStatusEnum.REPAY_OVERDUE.getCode());
	}
	
	@Override
	public Page<ItbRepayInfo> findQueryPage(final ItbRepayInfo example, Pageable page) {
		return itbRepayInfoRepository.findAll(new Specification<ItbRepayInfo>() {
			@Override
			public Predicate toPredicate(Root<ItbRepayInfo> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
					List<Predicate> params = new ArrayList<Predicate>();
				if (null != example.getProjectId()) {
					Path<String> loanContractNoPath = root.get("projectId");
					params.add(criteriaBuilder.equal(loanContractNoPath, example.getProjectId()));
				}
				Predicate[] predicates = new Predicate[params.size()];
				criteriaQuery.where(params.toArray(predicates));
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("duetoRepayDate")));
				return null;
			}
		}, page);
	}
}
