package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.epbcommons.transformation.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itanbank.account.common.AuthUtil;
import com.itanbank.account.common.JsonResult;
import com.itanbank.account.domain.app.enums.YesOrNoEnum;
import com.itanbank.account.domain.web.ItbAddAmountInfo;
import com.itanbank.account.domain.web.ItbAdvanceRecordInfo;
import com.itanbank.account.domain.web.ItbCapitalFlowInfo;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbInvestInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbPayInfo;
import com.itanbank.account.domain.web.ItbProjectAccount;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;
import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.ItbUserAccount;
import com.itanbank.account.domain.web.enums.AddAmountStatusEnum;
import com.itanbank.account.domain.web.enums.AdvanceRecordStatusEnum;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.domain.web.enums.CompanyStatusEnum;
import com.itanbank.account.domain.web.enums.CompanyTypeEnum;
import com.itanbank.account.domain.web.enums.IsAddAmountEnum;
import com.itanbank.account.domain.web.enums.IsAdvanceEnum;
import com.itanbank.account.domain.web.enums.ItbInvestStatusEnum;
import com.itanbank.account.domain.web.enums.LoanUserTypeEnum;
import com.itanbank.account.domain.web.enums.OrderStatusEnum;
import com.itanbank.account.domain.web.enums.PayStatusEnum;
import com.itanbank.account.domain.web.enums.ProjectStatusEnum;
import com.itanbank.account.domain.web.enums.RepayStatusEnum;
import com.itanbank.account.domain.web.enums.TimeUnitEnum;
import com.itanbank.account.domain.web.enums.TransferActionEnum;
import com.itanbank.account.domain.web.enums.UserTypeEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.ParticAccTypeEnum;
import com.itanbank.account.pay.enums.ParticTypeEnum;
import com.itanbank.account.pay.enums.ProjectTransferNopwdAccType;
import com.itanbank.account.pay.enums.ProjectTransferNopwdParticType;
import com.itanbank.account.pay.enums.ProjectTransferNopwdSevType;
import com.itanbank.account.pay.enums.ProjectUpdateChangeTypeEnum;
import com.itanbank.account.pay.enums.ProjectUpdateStateEnum;
import com.itanbank.account.pay.enums.TransActionEnum;
import com.itanbank.account.pay.model.BasicModel;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdRequest;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdResult;
import com.itanbank.account.pay.model.project.ProjectTransferRequest;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import com.itanbank.account.pay.model.project.ProjectUpdateRequest;
import com.itanbank.account.pay.model.project.ProjectUpdateResult;
import com.itanbank.account.pay.model.query.ProjectRequest;
import com.itanbank.account.pay.model.query.ProjectResult;
import com.itanbank.account.pay.service.ProjectService;
import com.itanbank.account.pay.service.QueryService;
import com.itanbank.account.repository.web.ItbProjectInfoRepository;

@Service
@Transactional
public class ItbProjectInfoServiceImpl implements ItbProjectInfoService{
	
	@Autowired
	private ItbProjectInfoRepository projectInfoRepository;
	
	@Autowired
	private ItbProjectAccountService itbProjectAccountService;
	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private QueryService queryService;
	@Autowired
	private ItbOrderInfoService itbOrderInfoService;
	@Autowired
	private ItbInvestService investService;
	@Autowired
	private ItbCapitalFlowService itbCapitalFlowService;
	@Autowired
	private ItbUserService 	itbUserService;
	@Autowired
	private ItbUserAccountService itbUserAccountService;
	@Autowired
	private ItbRepayInfoService itbRepayInfoService;
	@Autowired
	private ItbPayService itbPayService;
	@Autowired
	private ItbAdvanceRecordService itbAdvanceRecordService;
	@Autowired
	private ItbAddAmountInfoService itbAddAmountInfoService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@Override
	public ItbProjectInfo findById(Long id){
		return projectInfoRepository.findOne(id);
	}

	/**
	 * 根据状态查询
	 * @param status
	 * @return
	 */
	@Override
	public List<ItbProjectInfo> findByStatus(String status){
		return projectInfoRepository.findByStatus(status);
	}

	/**
	 * 查询未完成的返款记录
	 * @return
	 */
	@Override
	public List<ItbProjectInfo> findForAdvance(){
		return projectInfoRepository.findForAdvance();
	}

	@Override
	public Page<ItbProjectInfo> findPage(final ItbProjectInfo example, Pageable page) {
		return projectInfoRepository.findAll(new Specification<ItbProjectInfo>() {
            @Override
            public Predicate toPredicate(Root<ItbProjectInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> params = new ArrayList<Predicate>();
                if(StringUtils.isNotBlank(example.getStatus())){
                	if(ProjectStatusEnum.ADVANCELIST.getCode().equals(example.getStatus())){
                		
                		Path<String> usernamePath = root.get("status");
                		params.add(criteriaBuilder.equal(usernamePath,ProjectStatusEnum.LATEREPAY.getCode()));
                		
                		Subquery<ItbAdvanceRecordInfo> subquery =criteriaQuery.subquery(ItbAdvanceRecordInfo.class);  
                        Root<ItbAdvanceRecordInfo> advanceRecordInfo = subquery.from(ItbAdvanceRecordInfo.class);  
                        subquery.select(advanceRecordInfo);
                        List<Predicate> subQueryPredicates = new ArrayList<Predicate>(); 
                        subQueryPredicates.add(criteriaBuilder.equal(root.get("id"), advanceRecordInfo.get("projectId")));
                        subQueryPredicates.add(criteriaBuilder.or(criteriaBuilder.equal(advanceRecordInfo.get("status"), AdvanceRecordStatusEnum.FAIL.getCode()),criteriaBuilder.equal(advanceRecordInfo.get("status"), AdvanceRecordStatusEnum.SAVE.getCode())));
                        subquery.where(subQueryPredicates.toArray(new Predicate[]{})); 
                        params.add(criteriaBuilder.exists(subquery));
                        
                	}else if(ProjectStatusEnum.DISCOUNTLIST.getCode().equals(example.getStatus())){
                		/*Path<String> usernamePath = root.get("status");
                		params.add(criteriaBuilder.equal(usernamePath,ProjectStatusEnum.BACK.getCode()));
                		
                		Subquery<ItbProjectAccount> subquery =criteriaQuery.subquery(ItbProjectAccount.class);  
                        Root<ItbProjectAccount> itbProjectAccount = subquery.from(ItbProjectAccount.class);  
                        subquery.select(itbProjectAccount);
                        List<Predicate> subQueryPredicates = new ArrayList<Predicate>(); 
                        subQueryPredicates.add(criteriaBuilder.equal(root.get("id"), itbProjectAccount.get("id")));
                        subQueryPredicates.add(criteriaBuilder.equal(itbProjectAccount.get("isAddAmount"), IsAddAmountEnum.YES.getCode()));
                        subquery.where(subQueryPredicates.toArray(new Predicate[]{})); 
                        params.add(criteriaBuilder.exists(subquery));*/
						Path<String> usernamePath = root.get("status");
						params.add(criteriaBuilder.equal(usernamePath, ProjectStatusEnum.DISCOUNTING.getCode()));
                		
                	}else{
                		Path<String> usernamePath = root.get("status");
                		params.add(criteriaBuilder.equal(usernamePath,example.getStatus()));
                	}
                }
                if(StringUtils.isNotBlank(example.getLoanName())){
                    Path<String> loanNamePath = root.get("loanName");
                    params.add(criteriaBuilder.like(loanNamePath, "%"+example.getLoanName()+"%"));
                }
                if(StringUtils.isNotBlank(example.getProjectName())){
                    Path<String> projectNamePath = root.get("projectName");
                    params.add(criteriaBuilder.like(projectNamePath, "%"+example.getProjectName()+"%"));
                }
                if(StringUtils.isNotBlank(example.getLoanContractNo())){
                    Path<String> loanContractNoPath = root.get("loanContractNo");
                    params.add(criteriaBuilder.equal(loanContractNoPath,example.getLoanContractNo()));
                }
                Predicate[] predicates = new Predicate[params.size()];
                criteriaQuery.where(params.toArray(predicates));
                //criteriaQuery.orderBy(criteriaBuilder.desc(root.get("endDate")));
                return null;
            }
        }, page);
	}
	
	@Override
	public ItbProjectInfo getProjectInfoById(Long id){
		return projectInfoRepository.findOne(id);
	}
	@Override
	public ItbProjectInfo saveItbProjectInfo(ItbProjectInfo itbProjectInfo){
		return projectInfoRepository.save(itbProjectInfo);
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult fullProject(Long id,String ip) throws Exception{
		//第三方标的ID
		String payProjectId = null;
		//第三方投资人ID
		String payCompanyId = "";
		ItbProjectInfo itbProjectInfo = getProjectInfoById(id);
		if(!ProjectStatusEnum.INVESTMENT.getCode().equals(itbProjectInfo.getStatus())){
			logger.info("标的状态不符,请刷新后重试");
			return new JsonResult(9999,"标的状态不符,请刷新后重试！");
		}
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(id);
		payProjectId = itbProjectAccount.getPayProjectId();
		//已筹金额
		double putAmount = itbProjectAccount.getPutAmount();
		//标的金额
		double loanAmount = itbProjectInfo.getLoanAmount();
		//可投金额
		double canAmount = itbProjectAccount.getCanAmount();
		
		if(!MathUtil.equals(canAmount, MathUtil.sub(loanAmount, putAmount))){
			//有冻结金额禁止满标
			return new JsonResult(9999,"有用户正在投标,请稍后进行满标操作");
		}
		
		//待满标金额
		double fullAmount = MathUtil.sub(loanAmount, putAmount);
		//校验标的余额
		logger.info("校验标的余额");
		ProjectRequest projectRequest = new ProjectRequest(); 
		projectRequest.setProject_id(payProjectId.toString());
		ProjectResult projectResult = queryService.project(projectRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectResult.getRet_code())){
			if(MathUtil.mul(putAmount, 100)!=Double.parseDouble(projectResult.getBalance())){
				logger.error("标的已筹金额和第三方不一致!请检查---本地余额："+MathUtil.mul(putAmount, 100)+",第三方余额："+projectResult.getBalance());
				return new JsonResult(9999,"标的已筹金额和第三方不一致!请检查");
			}
		}
		//查询企业投资人
		ItbCompanyInfo companyInfo = new ItbCompanyInfo();
		List<ItbCompanyInfo> companyInfos = itbCompanyInfoService.findByTypeAndStatus(CompanyTypeEnum.INVESTMENT.getCode(), CompanyStatusEnum.ENABLE.getCode());
		if(CollectionUtils.isNotEmpty(companyInfos)){
			companyInfo = companyInfos.get(0);
			payCompanyId =  companyInfo.getPayId();
		}
		logger.info("查询企业投资人成功,PayId:"+companyInfo.getPayId()+",name="+companyInfo.getName());
		//生成订单号
		ItbOrderInfo orderInfo = itbOrderInfoService.addEnterpriseProjectTransferOrderInfo(companyInfo, itbProjectInfo, BusinessTypeEnum.INVEST, fullAmount,ip);
		logger.info("生成订单号成功："+orderInfo.getOrderNo());
		//组装请求参数
		ProjectTransferNopwdRequest projectTransferNopwd = new ProjectTransferNopwdRequest();
		projectTransferNopwd.setOrder_id(orderInfo.getOrderNo());
		projectTransferNopwd.setProject_id(payProjectId.toString());
		projectTransferNopwd.setServ_type(ProjectTransferNopwdSevType.INVEST.getCode());;
		projectTransferNopwd.setPartic_type(ProjectTransferNopwdParticType.INVEST.getCode());;
		projectTransferNopwd.setPartic_acc_type(ProjectTransferNopwdAccType.ENTERPRISE.getCode());
		projectTransferNopwd.setPartic_user_id(payCompanyId);
		projectTransferNopwd.setAmount(String.valueOf(Math.round(MathUtil.mul(fullAmount, 100))));
		projectTransferNopwd.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/project/transferNopwdNotify/fullProject.html");
		//执行满标操作
		logger.info("执行满标操作");
		ProjectTransferNopwdResult ptrNopwdResult =  projectService.transferNopwd(projectTransferNopwd);
		if(ExecuetResultCode.SCUESS.getCode().equals(ptrNopwdResult.getRet_code())||ExecuetResultCode.MIDDLE_STATE.getCode().equals(ptrNopwdResult.getRet_code())){
			//更新标的状态为满标中
			itbProjectInfo.setStatus(ProjectStatusEnum.FULLING.getCode());
			ItbProjectInfo newItbProjectInfo = findById(itbProjectInfo.getId());
			if(!newItbProjectInfo.getStatus().equals(ProjectStatusEnum.FULL.getCode())){
				saveItbProjectInfo(itbProjectInfo);
			}
			return new JsonResult();
		}else{
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(ptrNopwdResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("满标结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"满标结果成功，更新订单表失败");
			}
			return new JsonResult(Integer.parseInt(ptrNopwdResult.getRet_code()),ptrNopwdResult.getRet_msg());
		}
		
	}
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult fullProjectResult(ProjectTransferNopwdResult ptrNopwdResult) throws Exception{
		String orderNo = ptrNopwdResult.getOrder_id();
		ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
		double fullAmount = orderInfo.getAmount();
		Long projectId = orderInfo.getProjectId();
		ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
    	if(ExecuetResultCode.SCUESS.getCode().equals(ptrNopwdResult.getRet_code())){
			logger.info("满标成功：修改订单状态、添加投资记录、添加资金流水、修改企业账户余额、修改标的为满标");
			// 修改订单状态为支付完成
			String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(ptrNopwdResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("满标结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    throw new Exception("满标结果成功，更新订单表失败");
			}
			logger.info("修改订单状态为支付完成");
			//添加投资记录
			ItbInvestInfo itbInvestInfo = getItbInvestInfo(projectId, itbProjectInfo, fullAmount, companyInfo);
			itbInvestInfo =  investService.saveItbInvest(itbInvestInfo);
			logger.info("添加投资记录成功");
			// 添加资金流水
			ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, itbInvestInfo.getId(),UserTypeEnum.ENTERPRISE, ptrNopwdResult.getTrade_no(),TransferActionEnum.PROJECT_IN);
			itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfo);
			logger.info("添加资金流水成功");
			//修改企业账户余额
			// 修改企业账户
	        isPass = itbCompanyInfoService.updateBalanceByIdAndVersion(-orderInfo.getAmount(), companyInfo.getId(), companyInfo.getVersion());
	        if(!isPass){
	            logger.error("满标业务处理失败，更新企业账户表失败。 订单信息：{}", orderInfo);
	            throw new Exception("满标业务处理失败，更新企业账户表失败。 订单信息：" + orderInfo);
	        }
			logger.info("修改企业账户余额成功");
			//修改标的为满标成功
			itbProjectInfo.setStatus(ProjectStatusEnum.FULL.getCode());
			boolean updateProjectAccount =  itbProjectAccountService.updateFullProjectByIdAndVersion(fullAmount, itbProjectAccount.getId(), itbProjectAccount.getVersion());
			if(!updateProjectAccount){
				 logger.error("满标结果成功，更新标的余额失败。 订单信息：{}", orderInfo);
				throw new Exception("满标结果成功，更新标的余额失败");
			}
			saveItbProjectInfo(itbProjectInfo);
			logger.info("修改本地标的为满标成功");
			return new JsonResult();
		}else{
			//修改标的状态为投资中
			itbProjectInfo.setStatus(ProjectStatusEnum.INVESTMENT.getCode());
			saveItbProjectInfo(itbProjectInfo);
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(ptrNopwdResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("满标结果失败，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"满标结果失败，更新订单表失败");
			}
			return new JsonResult(Integer.parseInt(ptrNopwdResult.getRet_code()),ptrNopwdResult.getRet_msg());
		}
		
	}
	/**
	 * 放款申请
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult pay(Long projectId,String ip) throws Exception{
		//第三方标的ID
		String payProjectId = null;
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		if(!ProjectStatusEnum.CHARGED.getCode().equals(itbProjectInfo.getStatus())){
			logger.info("标的状态不符,请刷新后重试");
			return new JsonResult(9999,"标的状态不符,请刷新后重试！");
		}
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		payProjectId = itbProjectAccount.getPayProjectId();
		//标的余额
		double projectBalance = itbProjectAccount.getBalance();
		//实际放款额
		double grantPayAmount = itbProjectAccount.getGrantPayAmount();
		//校验标的余额
		logger.info("校验标的余额");
		ProjectRequest projectRequest = new ProjectRequest(); 
		projectRequest.setProject_id(payProjectId.toString());
		ProjectResult projectResult = queryService.project(projectRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectResult.getRet_code())){
			if(MathUtil.mul(projectBalance, 100)!=Double.parseDouble(projectResult.getBalance())){
				logger.error("标的余额和第三方不一致!请检查---本地余额："+MathUtil.mul(projectBalance, 100)+",第三方余额："+projectResult.getBalance());
				return new JsonResult(9999,"标的余额和第三方不一致!请检查");
			}
		}
		ItbOrderInfo orderInfo = null;
		//组装请求参数
		ProjectTransferRequest projectTransferRequest = new ProjectTransferRequest();
		//查询此标的融资人
		if(LoanUserTypeEnum.COMPANY.getCode().equals(itbProjectInfo.getLoanUserType())){
			//如果是企业借款人
			ItbCompanyInfo companyInfo =itbCompanyInfoService.findById(itbProjectInfo.getLoanUserId());
			logger.info("查询此标的融资人,PayId:"+companyInfo.getPayId()+",name="+companyInfo.getName());
			//生成订单号
			orderInfo = itbOrderInfoService.addEnterpriseProjectTransferOrderInfo(companyInfo, itbProjectInfo, BusinessTypeEnum.PAY, grantPayAmount,ip);
			projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.COMPANY.getCode());
			projectTransferRequest.setPartic_user_id(companyInfo.getPayId());
		}else{
			//查询客户信息
			ItbUser itbUser = itbUserService.findById(itbProjectInfo.getLoanUserId());
			//生成订单号
			orderInfo = itbOrderInfoService.addPersonProjectTransferOrderInfo(itbUser, itbProjectInfo, BusinessTypeEnum.PAY, grantPayAmount, ip);
			projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.PERSON.getCode());
			projectTransferRequest.setPartic_user_id(itbUser.getPayId());
		}
		
 		logger.info("生成订单号成功："+orderInfo.getOrderNo());
		projectTransferRequest.setOrder_id(orderInfo.getOrderNo());
		projectTransferRequest.setProject_id(payProjectId.toString());
		projectTransferRequest.setTrans_action(TransActionEnum.OUT.getCode());
		projectTransferRequest.setServ_type(BusinessTypeEnum.PAY.getPayCode());
		projectTransferRequest.setPartic_type(ParticTypeEnum.LOAN.getCode());
		projectTransferRequest.setAmount(String.valueOf(Math.round(MathUtil.mul(grantPayAmount, 100))));
		projectTransferRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/project/transferNotify/pay.html");
		projectTransferRequest.setRet_url(projectTransferRequest.getNotify_url());
		//执行放款操作
		logger.info("执放款操作");
		ProjectTransferResult projectTransferResult =  projectService.transfer(projectTransferRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())||ExecuetResultCode.MIDDLE_STATE.getCode().equals(projectTransferResult.getRet_code())){
			//更新标的状态为放款
			itbProjectInfo.setStatus(ProjectStatusEnum.PAY.getCode());
			saveItbProjectInfo(itbProjectInfo);
			return new JsonResult();
		}else{
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("放款结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"放款结果成功，更新订单表失败");
			}
			logger.info("放款结果成功，更新订单表成功");
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}

	/**
	 * 放款接口通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult payResult(ProjectTransferResult projectTransferResult) throws Exception{
		//更新订单表，添加资金流水，转账流水，修改标的状态，标的金额，生成还款，调用标的更新接口修改为还款中
		String orderNo = projectTransferResult.getOrder_id();
		ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
		double grantPayAmount = orderInfo.getAmount();
		Long projectId = orderInfo.getProjectId();
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		//是否企业借款人
		boolean isCompany = null!=orderInfo.getUserId()?true:false;
		ItbCompanyInfo companyInfo = null;
		ItbUserAccount itbUserAccount = null;
		if(isCompany){
			companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());
		}else{
			itbUserAccount = itbUserAccountService.findById(orderInfo.getUserId());
		}
    	if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())){
			logger.info("放款结果成功");
			// 修改订单状态为支付完成
			String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("放款结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    throw new Exception("放款结果成功，更新订单表失败。订单信息：{}"+orderInfo);
			}
			logger.info("修改订单状态为支付完成");
			//添加还款信息
			ItbCompanyInfo advanceCompany = itbCompanyInfoService.findById(itbProjectAccount.getAdvanceCompanyId());
			ItbRepayInfo repayInfo = getItbRepayInfo(itbProjectInfo, itbProjectAccount, advanceCompany);
			repayInfo = itbRepayInfoService.save(repayInfo);
			// 添加资金流水
			ItbCapitalFlowInfo capitalFlowInfo  = null;
			if(isCompany){
				//企业
				capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, repayInfo.getId(),UserTypeEnum.ENTERPRISE, projectTransferResult.getTrade_no(),TransferActionEnum.PROJECT_OUT);
			}else{
				capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, repayInfo.getId(),UserTypeEnum.PERSON, projectTransferResult.getTrade_no(),TransferActionEnum.PROJECT_OUT);
			}
			itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfo);
			logger.info("添加资金流水成功");
			//添加放款记录
			ItbPayInfo itbPayInfo = null;
			if(isCompany){
				itbPayInfo = getItbPayInfo(orderNo, grantPayAmount, companyInfo.getId(), projectId, UserTypeEnum.ENTERPRISE);
			}else{
				itbPayInfo = getItbPayInfo(orderNo, grantPayAmount, itbUserAccount.getId(), projectId, UserTypeEnum.PERSON);
			}
			itbPayService.save(itbPayInfo);
			logger.info("添加放款记录成功");
			//更新账户余额
			if(isCompany){
//				companyInfo.setBalance(MathUtil.add(companyInfo.getBalance(), grantPayAmount));
//				itbCompanyInfoService.saveItbCompanyInfo(companyInfo);
				boolean updateitbCompany = itbCompanyInfoService.updateBalanceByIdAndVersion(grantPayAmount, companyInfo.getId(), companyInfo.getVersion());
				if(!updateitbCompany){
				    logger.error("放款结果成功，更新企业用户余额失败。 订单信息：{}", orderInfo);
					throw new Exception("放款结果成功，更新企业用户余额失败");
				}
			}else{
//				itbUserAccount.setTotalAmount(MathUtil.add(itbUserAccount.getTotalAmount(), grantPayAmount));
//				itbUserAccount.setUsedAmount(MathUtil.add(itbUserAccount.getUsedAmount(), grantPayAmount));
//				itbUserAccountService.save(itbUserAccount);
				boolean updateitbUserAccountPass =  itbUserAccountService.updateBalanceByIdAndVersion(grantPayAmount, itbUserAccount.getId(), itbUserAccount.getVersion());
				if(!updateitbUserAccountPass){
				    logger.error("放款结果成功，更新个人用户余额失败。 订单信息：{}", orderInfo);
				    throw new Exception("放款结果成功，更新个人用户余额失败");
				}
			}
			logger.info("更新账户余额成功");
			//修改标的状态为还款中
			ProjectUpdateRequest projectUpdate = new ProjectUpdateRequest();
			projectUpdate.setProject_id(itbProjectAccount.getPayProjectId().toString());
			projectUpdate.setChange_type(ProjectUpdateChangeTypeEnum.STATUS.getCode());
			projectUpdate.setProject_state(ProjectUpdateStateEnum.REPAY.getCode());
			ProjectUpdateResult updateResult = projectService.update(projectUpdate);
			if(ExecuetResultCode.SCUESS.getCode().equals(updateResult.getRet_code())){
				//修改标的为放款成功
				logger.info("修改第三方标的为放款成功");
				itbProjectInfo.setStatus(ProjectStatusEnum.REPAY.getCode());
				saveItbProjectInfo(itbProjectInfo);
//				itbProjectAccount.setBalance(MathUtil.sub(itbProjectAccount.getBalance(), grantPayAmount));
//				itbProjectAccountService.saveProjectAccount(itbProjectAccount);
				boolean updateProjectAccount =  itbProjectAccountService.updateBalanceByIdAndVersion(-grantPayAmount, itbProjectAccount.getId(), itbProjectAccount.getVersion());
				if(!updateProjectAccount){
					 logger.error("放款结果成功，更新标的余额失败。 订单信息：{}", orderInfo);
					throw new Exception("放款结果成功，更新标的余额失败");
				}
				logger.info("修改本地标的为放款成功");
			}else{
				logger.error("修改第三方标的为还款中成功接口返回失败");
				throw new Exception("修改第三方标的为还款中成功接口返回失败");
			}
			return new JsonResult();
		}else{
			//修改标的状态为已满标
			itbProjectInfo.setStatus(ProjectStatusEnum.FULL.getCode());
			saveItbProjectInfo(itbProjectInfo);
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("放款结果失败，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"放款结果失败，更新订单表失败");
			}
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}

	/**
	 * 平台收费
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult charge(Long projectId, String ip) throws Exception {
		//第三方标的ID
		String payProjectId = null;
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		if(!ProjectStatusEnum.FULL.getCode().equals(itbProjectInfo.getStatus())){
			logger.info("标的状态不符,请刷新后重试");
			return new JsonResult(9999,"标的状态不符,请刷新后重试！");
		}
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		payProjectId = itbProjectAccount.getPayProjectId();
		//标的余额
		double projectBalance = itbProjectAccount.getBalance();
		
		//服务费=应还利息+贴现-实发收益+应收管理费
		double serverAmount = itbProjectAccount.getServerAmount()==null?0d:itbProjectAccount.getServerAmount();
		double preInterest = itbProjectAccount.getPreInterest()==null?0d:itbProjectAccount.getPreInterest();
		double addAmount = itbProjectAccount.getAddAmount()==null?0d:itbProjectAccount.getAddAmount();
		double sumInterest = investService.queryProjectSumInterest(projectId);
		serverAmount = MathUtil.add(MathUtil.sub(MathUtil.add(preInterest, addAmount), sumInterest),serverAmount);
		
		//校验标的余额
		logger.info("校验标的余额");
		ProjectRequest projectRequest = new ProjectRequest(); 
		projectRequest.setProject_id(payProjectId.toString());
		ProjectResult projectResult = queryService.project(projectRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectResult.getRet_code())){
			if(MathUtil.mul(projectBalance, 100)!=Double.parseDouble(projectResult.getBalance())){
				logger.error("标的余额和第三方不一致!请检查---本地余额："+MathUtil.mul(projectBalance, 100)+",第三方余额："+projectResult.getBalance());
				return new JsonResult(9999,"标的余额和第三方不一致!请检查");
			}
		}
		ItbOrderInfo orderInfo = null;
		//查询平台企业账户
		ItbCompanyInfo companyInfo = null;
		List<ItbCompanyInfo> companyInfos = itbCompanyInfoService.findByTypeAndStatus(CompanyTypeEnum.PLATFORM.getCode(), CompanyStatusEnum.ENABLE.getCode());
		if(CollectionUtils.isNotEmpty(companyInfos)){
			companyInfo = companyInfos.get(0);
		}
		logger.info("查询平台企业账户成功,PayId:"+companyInfo.getPayId()+",name="+companyInfo.getName());
		//生成订单号
		orderInfo = itbOrderInfoService.addEnterpriseProjectTransferOrderInfo(companyInfo, itbProjectInfo, BusinessTypeEnum.SERVER_FEE, serverAmount,ip);
		
		logger.info("生成订单号成功："+orderInfo.getOrderNo());
		//组装请求参数
		ProjectTransferRequest projectTransferRequest = new ProjectTransferRequest();
		projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.COMPANY.getCode());
		projectTransferRequest.setPartic_user_id(companyInfo.getPayId());
		projectTransferRequest.setOrder_id(orderInfo.getOrderNo());
		projectTransferRequest.setProject_id(payProjectId.toString());
		projectTransferRequest.setTrans_action(TransActionEnum.OUT.getCode());
		projectTransferRequest.setServ_type(BusinessTypeEnum.SERVER_FEE.getPayCode());
		projectTransferRequest.setPartic_type(ParticTypeEnum.P2P.getCode());
		projectTransferRequest.setAmount(String.valueOf(Math.round(MathUtil.mul(serverAmount, 100))));
		projectTransferRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/project/transferNotify/charge.html");
		projectTransferRequest.setRet_url(projectTransferRequest.getNotify_url());
		//执行平台收费操作
		logger.info("平台收费操作");
		ProjectTransferResult projectTransferResult =  projectService.transfer(projectTransferRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())||ExecuetResultCode.MIDDLE_STATE.getCode().equals(projectTransferResult.getRet_code())){
			//更新标的状态为平台收费中
			ItbProjectInfo newProjectInfo = findById(itbProjectInfo.getId());
			if(!newProjectInfo.getStatus().equals(ProjectStatusEnum.CHARGED.getCode())){
				itbProjectInfo.setStatus(ProjectStatusEnum.CHARGING.getCode());
				saveItbProjectInfo(itbProjectInfo);
			}
			return new JsonResult();
		}else{
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("平台收费结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"平台收费结果成功，更新订单表失败");
			}
			logger.info("平台收费结果成功，更新订单表成功");
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}

	/**
	 * 平台收费结果通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult chargeResult(ProjectTransferResult projectTransferResult) throws Exception {
		//更新订单表，添加资金流水，转账流水，修改标的状态，标的金额，企业余额
		String orderNo = projectTransferResult.getOrder_id();
		ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
		double serverAmount = orderInfo.getAmount();
		Long projectId = orderInfo.getProjectId();
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());

		if(!OrderStatusEnum.PAYMENT.getCode().equals(orderInfo.getOrderStatus())){
			logger.error("平台收费结果失败, 订单非支付中。");
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}

    	if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())){
			logger.info("平台收费结果成功");
			// 修改订单状态为支付完成
			String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("平台收费结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    throw new Exception("平台收费结果成功，更新订单表失败。订单信息：{}"+orderInfo);
			}
			logger.info("修改订单状态为支付完成");
			// 添加资金流水
			ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, null,UserTypeEnum.ENTERPRISE, projectTransferResult.getTrade_no(),TransferActionEnum.PROJECT_OUT);
			itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfo);
			logger.info("添加资金流水成功");
			//添加平台收费记录
			logger.info("添加平台收费记录成功");
			//更新账户余额
			boolean updateitbCompany = itbCompanyInfoService.updateBalanceByIdAndVersion(serverAmount, companyInfo.getId(), companyInfo.getVersion());
			if(!updateitbCompany){
			    logger.error("平台收费结果成功，更新企业用户余额失败。 订单信息：{}", orderInfo);
				throw new Exception("平台收费结果成功，更新企业用户余额失败");
			}
			logger.info("更新账户余额成功");
			//修改标的为平台收费成功
			logger.info("修改第三方标的为平台收费成功");
			itbProjectInfo.setStatus(ProjectStatusEnum.CHARGED.getCode());
			saveItbProjectInfo(itbProjectInfo);
			boolean updateProjectAccount = itbProjectAccountService.updateServerAmountByIdAndVersion(-serverAmount, serverAmount, itbProjectAccount.getId(), itbProjectAccount.getVersion());
			if(!updateProjectAccount){
				 logger.error("平台收费结果成功，更新标的余额失败。 订单信息：{}", orderInfo);
				throw new Exception("平台收费结果成功，更新标的余额失败");
			}
			logger.info("修改本地标的为平台收费成功");
			return new JsonResult();
		}else{
			//修改标的状态为已满标
			itbProjectInfo.setStatus(ProjectStatusEnum.FULL.getCode());
			saveItbProjectInfo(itbProjectInfo);
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("平台收费结果失败，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"平台收费结果失败，更新订单表失败");
			}
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}
	/**
	 * 偿付
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult advance(Long projectId, String ip) throws Exception {
		//第三方标的ID
		String payProjectId = null;
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		if(!ProjectStatusEnum.LATEREPAY.getCode().equals(itbProjectInfo.getStatus())){
			logger.info("标的状态不符,请刷新后重试");
			return new JsonResult(9999,"标的状态不符,请刷新后重试！");
		}
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		payProjectId = itbProjectAccount.getPayProjectId();
		//标的余额
		double projectBalance = itbProjectAccount.getBalance();
		
		ItbAdvanceRecordInfo itbAdvanceRecordInfo = null;
		
		List<ItbAdvanceRecordInfo> itbAdvanceRecordInfos =  itbAdvanceRecordService.findByProjectId(itbProjectInfo.getId());
		if(CollectionUtils.isNotEmpty(itbAdvanceRecordInfos)){
			itbAdvanceRecordInfo = itbAdvanceRecordInfos.get(0);
		}
		if(null==itbAdvanceRecordInfo){
			logger.error("根据标的查询垫付记录异常,标的ID:"+itbProjectInfo.getId());
			throw new Exception("根据标的查询垫付记录异常,标的ID:"+itbProjectInfo.getId());
		}
		//代偿金额
		double advanceAmount = MathUtil.add(itbAdvanceRecordInfo.getInterestAmount()==null?0d:itbAdvanceRecordInfo.getInterestAmount(),
				itbAdvanceRecordInfo.getCapitalAmount()==null?0d:itbAdvanceRecordInfo.getCapitalAmount()) ;
		//校验标的余额
		logger.info("校验标的余额");
		ProjectRequest projectRequest = new ProjectRequest(); 
		projectRequest.setProject_id(payProjectId.toString());
		ProjectResult projectResult = queryService.project(projectRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectResult.getRet_code())){
			if(MathUtil.mul(projectBalance, 100)!=Double.parseDouble(projectResult.getBalance())){
				logger.error("标的余额和第三方不一致!请检查---本地余额："+MathUtil.mul(projectBalance, 100)+",第三方余额："+projectResult.getBalance());
				return new JsonResult(9999,"标的余额和第三方不一致!请检查");
			}
		}
		ItbOrderInfo orderInfo = null;
		//查询平台企业账户
		ItbCompanyInfo companyInfo = null;
		List<ItbCompanyInfo> companyInfos = itbCompanyInfoService.findByTypeAndStatus(CompanyTypeEnum.ADVANCE.getCode(), CompanyStatusEnum.ENABLE.getCode());
		if(CollectionUtils.isNotEmpty(companyInfos)){
			companyInfo = companyInfos.get(0);
		}
		logger.info("查询代偿企业账户成功,PayId:"+companyInfo.getPayId()+",name="+companyInfo.getName());
		//生成订单号
		orderInfo = itbOrderInfoService.addEnterpriseProjectTransferOrderInfo(companyInfo, itbProjectInfo, BusinessTypeEnum.ADVANCE, advanceAmount,ip);
		
		logger.info("生成订单号成功："+orderInfo.getOrderNo());
		//组装请求参数
		ProjectTransferRequest projectTransferRequest = new ProjectTransferRequest();
		projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.COMPANY.getCode());
		projectTransferRequest.setPartic_user_id(companyInfo.getPayId());
		projectTransferRequest.setOrder_id(orderInfo.getOrderNo());
		projectTransferRequest.setProject_id(payProjectId.toString());
		projectTransferRequest.setTrans_action(TransActionEnum.IN.getCode());
		projectTransferRequest.setServ_type(BusinessTypeEnum.ADVANCE.getPayCode());
		projectTransferRequest.setPartic_type(ParticTypeEnum.ADVANCE.getCode());
		projectTransferRequest.setAmount(String.valueOf(Math.round(MathUtil.mul(advanceAmount, 100))));
		projectTransferRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/project/transferNotify/advance.html");
		projectTransferRequest.setRet_url(projectTransferRequest.getNotify_url());
		//执行偿付操作
		logger.info("偿付操作");
		ProjectTransferResult projectTransferResult =  projectService.transfer(projectTransferRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())||ExecuetResultCode.MIDDLE_STATE.getCode().equals(projectTransferResult.getRet_code())){
			//更新偿付记录状态为偿付中
			ItbAdvanceRecordInfo newitbAdvanceRecordInfo =  itbAdvanceRecordService.findOne(itbAdvanceRecordInfo.getId());
			if(!newitbAdvanceRecordInfo.getStatus().equals(AdvanceRecordStatusEnum.SUCCESS.getCode())){
				itbAdvanceRecordInfo.setStatus(AdvanceRecordStatusEnum.ADVANCING.getCode());
				itbAdvanceRecordService.save(itbAdvanceRecordInfo);
			}
			return new JsonResult();
		}else{
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("偿付结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"偿付结果成功，更新订单表失败");
			}
			logger.info("偿付结果成功，更新订单表成功");
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}

	/**
	 * 偿付结果通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult advanceResult(ProjectTransferResult projectTransferResult) throws Exception {
		//更新订单表，偿付记录，添加资金流水，转账流水，标的金额，企业余额
		String orderNo = projectTransferResult.getOrder_id();
		ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
		double advanceAmount = orderInfo.getAmount();
		Long projectId = orderInfo.getProjectId();
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());
		ItbAdvanceRecordInfo itbAdvanceRecordInfo = null;
		
		List<ItbAdvanceRecordInfo> itbAdvanceRecordInfos =  itbAdvanceRecordService.findByProjectId(itbProjectInfo.getId());
		if(CollectionUtils.isNotEmpty(itbAdvanceRecordInfos)){
			itbAdvanceRecordInfo = itbAdvanceRecordInfos.get(0);
		}
		if(null==itbAdvanceRecordInfo){
			logger.error("偿付结果通知接口 根据标的查询垫付记录异常,标的ID:"+itbProjectInfo.getId());
			throw new Exception("偿付结果通知接口 根据标的查询垫付记录异常,标的ID:"+itbProjectInfo.getId());
		}
    	if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())){
			logger.info("偿付结果成功");
			// 修改订单状态为支付完成
			String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("偿付结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    throw new Exception("偿付结果成功，更新订单表失败。订单信息：{}"+orderInfo);
			}
			logger.info("修改订单状态为支付完成");
			//更新偿付记录
			itbAdvanceRecordInfo.setStatus(AdvanceRecordStatusEnum.SUCCESS.getCode());
			itbAdvanceRecordService.save(itbAdvanceRecordInfo);
			logger.info("更新偿付记录成功");
			//修改还款信息
			ItbRepayInfo itbRepayInfo = itbRepayInfoService.findByProjectId(itbProjectInfo.getId());
			itbRepayInfo.setAdvanceCapital(advanceAmount);
			itbRepayInfo.setAdvanceInterest(0d);
			itbRepayInfo.setAdvancePayNo(companyInfo.getPayId());
			itbRepayInfoService.save(itbRepayInfo);
			logger.info("添加还款信息成功");

			// 修改标的状态
			if(Double.parseDouble(itbProjectAccount.getIsAddAmount()) > 0){  // 需要贴现
				// 更新标的状态为贴现
				itbProjectInfo.setStatus(ProjectStatusEnum.DISCOUNTING.getCode());
			} else {
				// 更新标的状态为返款中
				itbProjectInfo.setStatus(ProjectStatusEnum.BACK.getCode());
			}
			saveItbProjectInfo(itbProjectInfo);

			// 添加资金流水
			ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, itbAdvanceRecordInfo.getId(),UserTypeEnum.ENTERPRISE, projectTransferResult.getTrade_no(),TransferActionEnum.PROJECT_IN);
			itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfo);
			logger.info("添加资金流水成功");
			//更新账户余额
			boolean updateitbCompany = itbCompanyInfoService.updateBalanceByIdAndVersion(-advanceAmount, companyInfo.getId(), companyInfo.getVersion());
			if(!updateitbCompany){
			    logger.error("偿付结果成功，更新代偿企业用户余额失败。 订单信息：{}", orderInfo);
				throw new Exception("偿付结果成功，更新代偿企业用户余额失败");
			}
			logger.info("更新账户余额成功");
			//修改标的为偿付成功
			logger.info("修改第三方标的为偿付成功");
			boolean updateProjectAccount =  itbProjectAccountService.updateAdvanceByIdAndVersion(advanceAmount,IsAdvanceEnum.YES.getCode(), itbProjectAccount.getId(), itbProjectAccount.getVersion());
			if(!updateProjectAccount){
				 logger.error("偿付结果成功，更新标的余额失败。 订单信息：{}", orderInfo);
				throw new Exception("偿付结果成功，更新标的余额失败");
			}
			logger.info("修改本地标的为偿付成功");
			return new JsonResult();
		}else{
			//更新偿付记录状态为失败
			itbAdvanceRecordInfo.setStatus(AdvanceRecordStatusEnum.FAIL.getCode());
			itbAdvanceRecordService.save(itbAdvanceRecordInfo);
			logger.info("更新偿付记录状态为失败成功");
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("偿付结果失败，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"偿付结果失败，更新订单表失败");
			}
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}
	
	/**
	 * 贴现
	 * @param projectId
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult discount(Long projectId, String ip) throws Exception {
		//第三方标的ID
		String payProjectId = null;
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		if(!ProjectStatusEnum.DISCOUNTING.getCode().equals(itbProjectInfo.getStatus())){
			logger.info("标的状态不符,请刷新后重试");
			return new JsonResult(9999,"标的状态不符,请刷新后重试！");
		}
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		payProjectId = itbProjectAccount.getPayProjectId();
		//标的余额
		double projectBalance = itbProjectAccount.getBalance();
		//贴现金额
		double discountAmount =itbProjectAccount.getAddAmount();
		//校验标的余额
		logger.info("校验标的余额");
		ProjectRequest projectRequest = new ProjectRequest(); 
		projectRequest.setProject_id(payProjectId.toString());
		ProjectResult projectResult = queryService.project(projectRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectResult.getRet_code())){
			if(!MathUtil.equals(MathUtil.mul(projectBalance, 100), Double.parseDouble(projectResult.getBalance()))){
				logger.error("标的余额和第三方不一致!请检查---本地余额："+MathUtil.mul(projectBalance, 100)+",第三方余额："+projectResult.getBalance());
				return new JsonResult(9999,"标的余额和第三方不一致!请检查");
			}
		}
		ItbOrderInfo orderInfo = null;
		//查询平台企业账户
		ItbCompanyInfo companyInfo = null;
		List<ItbCompanyInfo> companyInfos = itbCompanyInfoService.findByTypeAndStatus(CompanyTypeEnum.PLATFORM.getCode(), CompanyStatusEnum.ENABLE.getCode());
		if(CollectionUtils.isNotEmpty(companyInfos)){
			companyInfo = companyInfos.get(0);
		}
		logger.info("查询平台企业账户成功,PayId:"+companyInfo.getPayId()+",name="+companyInfo.getName());
		//生成订单号
		orderInfo = itbOrderInfoService.addEnterpriseProjectTransferOrderInfo(companyInfo, itbProjectInfo, BusinessTypeEnum.ADD_AMOUNT, discountAmount,ip);
		
		logger.info("生成订单号成功："+orderInfo.getOrderNo());
		//组装请求参数
		ProjectTransferRequest projectTransferRequest = new ProjectTransferRequest();
		projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.COMPANY.getCode());
		projectTransferRequest.setPartic_user_id(companyInfo.getPayId());
		projectTransferRequest.setOrder_id(orderInfo.getOrderNo());
		projectTransferRequest.setProject_id(payProjectId.toString());
		projectTransferRequest.setTrans_action(TransActionEnum.IN.getCode());
		projectTransferRequest.setServ_type(BusinessTypeEnum.ADD_AMOUNT.getPayCode());
		projectTransferRequest.setPartic_type(ParticTypeEnum.P2P.getCode());
		projectTransferRequest.setAmount(String.valueOf(Math.round(MathUtil.mul(discountAmount, 100))));
		projectTransferRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/project/transferNotify/discount.html");
		projectTransferRequest.setRet_url(projectTransferRequest.getNotify_url());
		//执行平台收费操作
		logger.info("贴现操作");
		ProjectTransferResult projectTransferResult =  projectService.transfer(projectTransferRequest);
		if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())||ExecuetResultCode.MIDDLE_STATE.getCode().equals(projectTransferResult.getRet_code())){
			//更新标的状态为贴现中
			itbProjectInfo.setStatus(ProjectStatusEnum.DISCOUNTING.getCode());
			saveItbProjectInfo(itbProjectInfo);
			return new JsonResult();
		}else{
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("贴现结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"贴现结果成功，更新订单表失败");
			}
			isPass = itbProjectAccountService.updateBalanceByIdAndVersion(orderInfo.getAmount(), itbProjectAccount.getId(), itbProjectAccount.getVersion());
			if(!isPass){
				logger.error("贴现结果成功，更新标的账户表失败。 订单信息：{}", orderInfo);
				return new JsonResult(9999,"贴现结果成功，更新订单表失败");
			}
			logger.info("贴现结果成功，更新订单表成功");
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}

	/**
	 * 贴现结果通知
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public JsonResult discountResult(ProjectTransferResult projectTransferResult) throws Exception {
		//更新订单表，添加资金流水，转账流水，修改标的状态，标的金额，企业余额
		String orderNo = projectTransferResult.getOrder_id();
		ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
		double discountAmount = orderInfo.getAmount();
		Long projectId = orderInfo.getProjectId();
		ItbProjectInfo itbProjectInfo = getProjectInfoById(projectId);
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.getProjectAccountById(projectId);
		ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId());
    	if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferResult.getRet_code())){
			logger.info("贴现结果成功");
			// 修改订单状态为支付完成
			String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("贴现结果成功，更新订单表失败。 订单信息：{}", orderInfo);
			    throw new Exception("贴现结果成功，更新订单表失败。订单信息：{}"+orderInfo);
			}
			logger.info("修改订单状态为支付完成");
			//添加贴现记录
			ItbAddAmountInfo addAmountInfo = getAddAmountInfo(discountAmount, itbProjectInfo);
			itbAddAmountInfoService.save(addAmountInfo);
			logger.info("添加贴现记录成功");
			// 添加资金流水
			ItbCapitalFlowInfo capitalFlowInfo = getItbCapitalFlowInfo(orderInfo, addAmountInfo.getId(), UserTypeEnum.ENTERPRISE, projectTransferResult.getTrade_no(),TransferActionEnum.PROJECT_IN);
			itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfo);
			logger.info("添加资金流水成功");
			//修改标的状态为返款中
			itbProjectInfo.setStatus(ProjectStatusEnum.BACK.getCode());
			saveItbProjectInfo(itbProjectInfo);
			logger.info("修改标的状态为返款中成功");
			//更新账户余额
			boolean updateitbCompany = itbCompanyInfoService.updateBalanceByIdAndVersion(-discountAmount, companyInfo.getId(), companyInfo.getVersion());
			if(!updateitbCompany){
			    logger.error("贴现结果成功，更新企业用户余额失败。 订单信息：{}", orderInfo);
				throw new Exception("贴现结果成功，更新企业用户余额失败");
			}
			logger.info("更新企业用户余额成功");
			boolean updateProjectAccount =  itbProjectAccountService.updateDiscountByIdAndVersion(discountAmount, IsAddAmountEnum.YES.getCode(), itbProjectAccount.getId(), itbProjectAccount.getVersion());
			if(!updateProjectAccount){
				 logger.error("贴现成功，更新标的余额失败。 订单信息：{}", orderInfo);
				throw new Exception("贴现结果成功，更新标的余额失败");
			}
			logger.info("更新账户余额成功");
			return new JsonResult();
		}else{
			//修改标的状态为返款中
			itbProjectInfo.setStatus(ProjectStatusEnum.BACK.getCode());
			saveItbProjectInfo(itbProjectInfo);
			String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
			boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(projectTransferResult.getTrade_no(), orderStatus, orderInfo.getId(), orderInfo.getVersion());
			if(!isPass){
			    logger.error("平台收费结果失败，更新订单表失败。 订单信息：{}", orderInfo);
			    return new JsonResult(9999,"平台收费结果失败，更新订单表失败");
			}
			return new JsonResult(Integer.parseInt(projectTransferResult.getRet_code()),projectTransferResult.getRet_msg());
		}
	}
	/**
	 * 锁定标的
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult lockProject(Long projectId) throws Exception{
		ItbProjectInfo itbProjectInfo = findById(projectId);
		if(itbProjectInfo.getLocked().equals(""+YesOrNoEnum.Y.getCode())){
			return new JsonResult(9999,"标的已锁定无需再次操作");
		}
		itbProjectInfo.setLocked(""+YesOrNoEnum.Y.getCode());
		saveItbProjectInfo(itbProjectInfo);
		return new JsonResult();
	}
	/**
	 * 获取贴现记录
	 * @param discountAmount
	 * @param itbProjectInfo
	 * @return
	 */
	private ItbAddAmountInfo getAddAmountInfo(double discountAmount, ItbProjectInfo itbProjectInfo) {
		ItbAddAmountInfo addAmountInfo = new ItbAddAmountInfo();
		addAmountInfo.setAddAmount(discountAmount);
		addAmountInfo.setAddRate(itbProjectInfo.getAddRate());
		addAmountInfo.setAddTime(new Date());
		addAmountInfo.setCreateTime(new Date());
		addAmountInfo.setCreateUserId(null);
		addAmountInfo.setProjectId(itbProjectInfo.getId());
		addAmountInfo.setStatus(AddAmountStatusEnum.SUCCESS.getCode());
		return addAmountInfo;
	}
	/**
	 * 获取放款
	 * @param orderNo
	 * @param grantPayAmount
	 * @param loanUserId
	 * @param projectId
	 * @param userTypeEnum
	 */
	private ItbPayInfo getItbPayInfo(String orderNo, double grantPayAmount,Long loanUserId,Long projectId,UserTypeEnum userTypeEnum) {
		ItbPayInfo itbPayInfo = new ItbPayInfo();
		itbPayInfo.setCreateTime(new Date());
		itbPayInfo.setLoanUserId(loanUserId);
		itbPayInfo.setLoanUserType(userTypeEnum.getCode());
		itbPayInfo.setOrderNo(orderNo);
		itbPayInfo.setPayAmount(grantPayAmount);
		itbPayInfo.setPayUserId(AuthUtil.getUserId());
		itbPayInfo.setProjectId(projectId);
		itbPayInfo.setStatus(PayStatusEnum.SUCCESS.getCode());
		itbPayInfo.setTime(new Date());
		return itbPayInfo;
	}

	/**
	 * 获取还款信息
	 * @param itbProjectInfo
	 * @param itbProjectAccount
	 * @param advanceCompany
	 * @return
	 */
	private ItbRepayInfo getItbRepayInfo(ItbProjectInfo itbProjectInfo, ItbProjectAccount itbProjectAccount,
			ItbCompanyInfo advanceCompany) {
		ItbRepayInfo repayInfo = new ItbRepayInfo();
		repayInfo.setAdvanceCapital(0d);
		repayInfo.setAdvanceInterest(0d);
		repayInfo.setAdvancePayNo(advanceCompany.getPayId());
		repayInfo.setAppId(itbProjectInfo.getAppId());
		repayInfo.setCreateTime(new Date());
		repayInfo.setDuetoReapyCapital(itbProjectInfo.getLoanAmount());
		repayInfo.setDuetoReapyInterest(itbProjectAccount.getPreInterest());
		repayInfo.setDuetoReapyPenalty(0d);
		repayInfo.setDuetoLateRepayServerAmount(0d);
		repayInfo.setDuetoRepayDate(itbProjectInfo.getEndDate());
		repayInfo.setLoanContractNo(itbProjectInfo.getLoanContractNo());
		repayInfo.setLoanUserId(itbProjectInfo.getLoanUserId());
		repayInfo.setPaidReapyCapital(0d);
		repayInfo.setPaidReapyInterest(itbProjectAccount.getPreInterest());
		repayInfo.setPaidReapyPenalty(0d);
		repayInfo.setPaidLateRepayServerAmount(0d);
		repayInfo.setProjectId(itbProjectInfo.getId());
		repayInfo.setStatus(RepayStatusEnum.REPAYING.getCode());
		repayInfo.setTerm(itbProjectInfo.getProjectTerm());
		return repayInfo;
	}

	/**
	 * 获取投资记录
	 * @param id
	 * @param itbProjectInfo
	 * @param fullAmount
	 * @param companyInfo
	 * @return
	 */
	private ItbInvestInfo getItbInvestInfo(Long id, ItbProjectInfo itbProjectInfo, double fullAmount, ItbCompanyInfo companyInfo) {
		ItbInvestInfo itbInvestInfo = new ItbInvestInfo();
		itbInvestInfo.setAmount(fullAmount);
		itbInvestInfo.setCreateTime(new Date());
		itbInvestInfo.setInvestUserType(UserTypeEnum.ENTERPRISE.getCode());
		itbInvestInfo.setProjectId(id);
		itbInvestInfo.setLoanContractNo(itbProjectInfo.getLoanContractNo());
		
		//计算收益
		double returnAmount = 0d;
		double returnRate = MathUtil.div(itbProjectInfo.getReturnRate(), 100, 10);
		Long loanDay = itbProjectInfo.getProjectTerm();
		if(TimeUnitEnum.MONTH.getCode().equals(itbProjectInfo.getTermUnit())){
			loanDay = itbProjectInfo.getProjectTerm()*30;
		}
		returnAmount = MathUtil.round(MathUtil.mul(MathUtil.mul(fullAmount, MathUtil.div(returnRate, 360, 10)), loanDay), 2);
		
		itbInvestInfo.setReturnAmount(returnAmount);
		itbInvestInfo.setReturnRate(itbProjectInfo.getReturnRate());
		itbInvestInfo.setStatus(ItbInvestStatusEnum.FINISH.getCode());
		itbInvestInfo.setTime(new Date());
		itbInvestInfo.setUserId(companyInfo.getId());
		itbInvestInfo.setUserName(companyInfo.getName());
		return itbInvestInfo;
	}
	/**
     * 创建流水记录
     * @param orderInfo 订单信息
     * @param businessId 业务id
     * @param userTypeEnum 用户类型
     * @param tradeNo 第三方流水号
     * @param actionEnum 转账方向
     * @return
     */
	@Override
	public ItbCapitalFlowInfo getItbCapitalFlowInfo(ItbOrderInfo orderInfo,Long businessId, UserTypeEnum userTypeEnum, String tradeNo,TransferActionEnum actionEnum){
        ItbCapitalFlowInfo capitalFlowInfo = new ItbCapitalFlowInfo();
        capitalFlowInfo.setBusinessId(businessId);
        capitalFlowInfo.setBusinessType(orderInfo.getBusinessType());
        capitalFlowInfo.setOrderNo(orderInfo.getOrderNo());
        capitalFlowInfo.setTradeNo(tradeNo);
        capitalFlowInfo.setAmount(orderInfo.getAmount());
        capitalFlowInfo.setTime(orderInfo.getOrderTime());
        capitalFlowInfo.setCreateTime(new java.util.Date());
        capitalFlowInfo.setUserId(orderInfo.getUserId()); // 企业借款
        capitalFlowInfo.setUserType(userTypeEnum.getCode());
        capitalFlowInfo.setProjectId(orderInfo.getProjectId());
        capitalFlowInfo.setTransAction(actionEnum.getCode());
        return capitalFlowInfo;
    }
}
