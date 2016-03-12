package com.itanbank.account.service.back;

import com.itanbank.account.domain.web.*;
import com.itanbank.account.domain.web.enums.*;
import com.itanbank.account.pay.enums.*;
import com.itanbank.account.pay.model.BasicModel;
import com.itanbank.account.pay.model.project.ProjectTransferRequest;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import com.itanbank.account.pay.model.project.ProjectUpdateRequest;
import com.itanbank.account.pay.model.project.ProjectUpdateResult;
import com.itanbank.account.pay.service.ProjectService;
import com.itanbank.account.repository.web.ItbBackAdvanceInfoRepository;
import com.itanbank.account.repository.web.ItbBackPlatformInfoRepository;
import com.itanbank.account.repository.web.ItbOrderInfoRepository;
import com.itanbank.account.repository.web.ItbRepayInfoRepository;
import com.itanbank.account.service.*;
import org.epbcommons.transformation.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 返款到垫付公司
 * Created by SDD on 2016/2/26.
 */
@Transactional
@Service
public class ItbBackAdvanceAmountServiceImpl implements ItbBackAmountService {
    @Autowired
    private OrderNoService orderNoService;

    @Autowired
    private ItbOrderInfoRepository itbOrderInfoRepository;

    @Autowired
    private ItbOrderInfoService itbOrderInfoService;

    @Autowired
    private ItbCompanyInfoService itbCompanyInfoService;

    @Autowired
    private ItbCapitalFlowService itbCapitalFlowService;

    @Autowired
    private ItbProjectAccountService itbProjectAccountService;

    @Autowired
    private ItbProjectInfoService itbProjectInfoService;

    @Autowired
    private ItbBackAdvanceInfoRepository itbBackAdvanceInfoRepository;

    @Autowired
    private ItbBackPlatformInfoRepository itbBackPlatformInfoRepository;

    @Autowired
    private ItbRepayInfoRepository itbRepayInfoRepository;

    @Autowired
    private ProjectService projectService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 检查是否还可以返款
     * @param userId 用户ID
     * @param projectId 标的ID
     * @param amount 本次返款金额
     * @return
     */
    private boolean isBack(Long userId, Long projectId, double amount){
        List<ItbOrderInfo> list = itbOrderInfoRepository.findPayInByCompany(userId, projectId, BusinessTypeEnum.ADVANCE_BACK.getCode());
        if(list != null) {

            // 返款记录订单之后(完成的订单或支付中的订单)
            double amountSum = 0d;
            for(ItbOrderInfo orderInfo : list) {
                amountSum = MathUtil.add(amountSum, orderInfo.getAmount());
            }
            amountSum = MathUtil.add(amountSum, amount);

            ItbRepayInfo repayInfo = itbRepayInfoRepository.findByProjectId(projectId);

            // 垫付本金+垫付利息+违约金
            double investAmountSum =  MathUtil.add(MathUtil.add(repayInfo.getAdvanceCapital(), repayInfo.getAdvanceCapital()), repayInfo.getDuetoReapyPenalty());

            if(amountSum > investAmountSum){
                return true;
            }
        }
        return false;
    }

    /**
     * 返款下订单
     * @param projectInfo
     * @param projectAccount
     * @param investInfo 投资记录，此处传null
     * @param ip
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @Override
    public ProjectTransferResult addOrderInfo(ItbProjectInfo projectInfo, ItbProjectAccount projectAccount, ItbInvestInfo investInfo, String ip) throws Exception{
        ItbCompanyInfo user = itbCompanyInfoService.findById(projectAccount.getAdvanceCompanyId()); // 查询垫付公司

        ItbRepayInfo repayInfo = itbRepayInfoRepository.findByProjectId(projectInfo.getId()); // 还款记录
        // 请求参数金额为元
        Double amount = MathUtil.add(MathUtil.add(repayInfo.getAdvanceCapital(), repayInfo.getAdvanceInterest()), repayInfo.getDuetoReapyPenalty()); // 垫付本金 + 垫付利息 + 应还违约金
        // 查询返款记录, 将已经返款的金额减去
        ItbBackAdvanceInfo backAdvanceInfo = itbBackAdvanceInfoRepository.findByProjectIdAndCompanyId(projectInfo.getId(), projectAccount.getAdvanceCompanyId());
        if(backAdvanceInfo != null){
            amount = MathUtil.sub(MathUtil.sub(MathUtil.sub(amount, backAdvanceInfo.getRepayCapital()), backAdvanceInfo.getRepayInterest()), backAdvanceInfo.getPenaltyAmount());
        }

        // 检查是否已经回款
        if (this.isBack(user.getId(), projectInfo.getId(), amount)){
            logger.info("垫付公司{}, 返款申请已经处理，本次无需重复操作", user.getName());
            return null;
        }

        // 获得订单号
        String orderNo = orderNoService.getOrderNo(BusinessTypeEnum.ADVANCE_BACK.getCode());

        ItbOrderInfo orderInfo = new ItbOrderInfo();
        orderInfo.setOrderNo(orderNo);	// 订单号
        orderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
        orderInfo.setBusinessType(BusinessTypeEnum.ADVANCE_BACK.getCode()); // 业务类型为还款后返款
        orderInfo.setAmount(amount); // 金额
        orderInfo.setOrderDesc(BusinessTypeEnum.ADVANCE_BACK.getDesc());  // 订单描述
        orderInfo.setUserId(user.getId());	// 企业ID
        orderInfo.setUserType(UserTypeEnum.ENTERPRISE.getCode()); // 企业代码
        orderInfo.setUserName(user.getName()); // 企业名称
        orderInfo.setBankCode(user.getBankCode()); // 银行代码

        orderInfo.setAppId(projectInfo.getAppId());
        orderInfo.setLoanContractNo(projectInfo.getLoanContractNo());
        orderInfo.setProjectId(projectInfo.getId());

        orderInfo.setOrderTime(new java.util.Date()); // 订单时间
        orderInfo.setInPayNo(user.getPayId()); // 用户第三方支付帐户号
        orderInfo.setOutPayNo(projectAccount.getPayProjectId());    // 转出方第三方支付账号

        orderInfo.setUserIp(ip);    // 用户IP
        orderInfo.setCreateTime(new java.util.Date()); // 创建时间
        orderInfo.setVersion(0L); // 版本号
        orderInfo.setRemarks(user.getId()+""); // 将垫付公司的ID放入到备注字段
        itbOrderInfoRepository.save(orderInfo);
        logger.info("返款到垫付方, 保存订单{}", orderInfo);

        ProjectTransferRequest projectTransferRequest = new ProjectTransferRequest();
        // 设置订单日期
        projectTransferRequest.setMer_date(new SimpleDateFormat("yyyyMMdd").format(orderInfo.getOrderTime()));
        // 设置订单号
        projectTransferRequest.setOrder_id(orderInfo.getOrderNo());
        projectTransferRequest.setRet_url(BasicModel.NOTIFY_SERVER+"pay/back/notify1.html");
        projectTransferRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/back/notify.html");
        projectTransferRequest.setProject_id(projectAccount.getPayProjectId()); // 第三方标的ID
        //projectTransferRequest.setProject_account_id(projectAccount.getPayProjectNo()); // 第三方标的号
        projectTransferRequest.setServ_type(ServTypeEnum.SERVER_FEE.getCode()); // 业务类型：还款后返款   // TODO 业务类型不行，就换成平台收费
        projectTransferRequest.setTrans_action(TransActionEnum.OUT.getCode()); // 转账方向：转出
        projectTransferRequest.setPartic_type(ParticTypeEnum.ADVANCE.getCode()); // 转账方类型: 担保方
        projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.COMPANY.getCode()); // 转账方账户类型 02 商户
        projectTransferRequest.setPartic_user_id(user.getPayId());  // 转账方用户号
        // 将元转换为分, 第三方请求的参数为分
        projectTransferRequest.setAmount(String.valueOf(Math.round(MathUtil.mul(amount, 100d))));

        // 请求第三方
        ProjectTransferResult projectTransferResult = projectService.transfer(projectTransferRequest);
        if(ExecuetResultCode.BALANCE_LOW.getCode().equals(projectTransferResult.getRet_code())){
            // 标的账户余额不足
            orderInfo.setOrderStatus(OrderStatusEnum.FAILURE_TO_PAY.getCode());
            itbOrderInfoService.save(orderInfo);
        }
        return projectTransferResult;
    }

    /**
     * 返款到垫付公司，业务处理
     * @param orderInfo
     * @param rojectTransferResult
     * @return
     */
    @Override
    public ExecuetResultCode back(ItbOrderInfo orderInfo, ItbProjectInfo projectInfo, ProjectTransferResult rojectTransferResult) throws Exception{
        String orderNo = rojectTransferResult.getOrder_id();  // 订单号
        String tradeNo = rojectTransferResult.getTrade_no();  // 第三方交易流水号

        ItbCompanyInfo companyInfo = itbCompanyInfoService.findById(orderInfo.getUserId()); // 查询垫付公司用户账户
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(orderInfo.getProjectId()); // 查询标的账户

        // 验证订单状态是否为支付中
        if(OrderStatusEnum.PAYMENT.equals(orderInfo.getOrderStatus())) {
            logger.error("返款到垫付公司业务处理失败, 订单目前状态不是支付中。 订单信息：{}", orderInfo);
            return ExecuetResultCode.E60781;
        }
        // 处理返回结果
        ExecuetResultCode resultCode = ExecuetResultCode.getByCode(rojectTransferResult.getRet_code());
        if(ExecuetResultCode.SCUESS.equals(resultCode)){ // 支付成功
            // 业务处理
            this.backSuccess(orderInfo, tradeNo, companyInfo, projectInfo, projectAccount, rojectTransferResult.getRet_msg());
        }else { // 支付失败
            this.backFailure(orderInfo, tradeNo, projectInfo, rojectTransferResult.getRet_msg());
        }
        return ExecuetResultCode.SCUESS;
    }

    /**
     * 更新标的状态
     * @param projectInfo
     * @param projectAccount
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void updateProjectStatus(ItbProjectInfo projectInfo, ItbProjectAccount projectAccount)  throws Exception{
        // 应还
        ItbRepayInfo repayInfo = itbRepayInfoRepository.findByProjectId(projectInfo.getId());
        double duetoAmount = MathUtil.add(MathUtil.add(MathUtil.add(repayInfo.getAdvanceCapital(), repayInfo.getAdvanceInterest()), repayInfo.getDuetoReapyPenalty()), repayInfo.getDuetoLateRepayServerAmount());

        // 已还
        double paidAmount = 0d;
        ItbBackAdvanceInfo backAdvanceInfo = itbBackAdvanceInfoRepository.findByProjectIdAndCompanyId(projectInfo.getId(), projectAccount.getAdvanceCompanyId());
        if(backAdvanceInfo != null) {
            paidAmount = MathUtil.add(MathUtil.add(backAdvanceInfo.getRepayCapital(), backAdvanceInfo.getRepayInterest()), backAdvanceInfo.getPenaltyAmount());
        }

        ItbBackPlatformInfo backPlatformInfo = itbBackPlatformInfoRepository.findByProjectId(projectInfo.getId());
        if(backPlatformInfo != null) {
            paidAmount = MathUtil.add(paidAmount, backPlatformInfo.getPaidLateRepayServerAmount());
        }

        // 判断是否还款完成
        if(MathUtil.equals(duetoAmount, paidAmount)){
            if(updateProjectStatus(projectAccount)) {
                projectInfo.setStatus(ProjectStatusEnum.END.getCode()); // 已还完
                itbProjectInfoService.saveItbProjectInfo(projectInfo);
            }
        }
    }

    /**
     * 更新第三方标的状态
     * @param projectAccount
     * @return
     * @throws Exception
     */
    private boolean updateProjectStatus(ItbProjectAccount projectAccount) throws Exception {
        ProjectUpdateRequest request = new ProjectUpdateRequest();
        request.setProject_id(projectAccount.getPayProjectId());
        request.setChange_type(ProjectUpdateChangeTypeEnum.STATUS.getCode());
        request.setProject_state(ProjectUpdateStateEnum.END.getCode());
        ProjectUpdateResult projectUpdateResult = projectService.update(request);
        ExecuetResultCode resultCode = ExecuetResultCode.getByCode(projectUpdateResult.getRet_code());
        if(ExecuetResultCode.SCUESS.equals(resultCode)){
            return true;
        }
        return false;
    }

    /**
     * 返款到垫付公司 - 失败业务处理
     * @param orderInfo
     * @param tradeNo
     * @param retMsg
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    private void backFailure(ItbOrderInfo orderInfo, String tradeNo, ItbProjectInfo projectInfo, String retMsg) throws Exception {
        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.FAILURE_TO_PAY.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("返款到垫付公司通知结果失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到垫付公司通知结果失败，更新订单表失败。 订单信息：" + orderInfo);
        }
        logger.info("返款到垫付公司通知结果失败，业务处理完成。 失败原因：{}， 订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 返款到垫付公司 - 成功业务处理
     * @param orderInfo
     * @param tradeNo
     * @param companyInfo
     * @param retMsg
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    private void backSuccess(ItbOrderInfo orderInfo, String tradeNo, ItbCompanyInfo companyInfo, ItbProjectInfo projectInfo, ItbProjectAccount projectAccount , String retMsg) throws Exception{
        // 生成转账记录 - 状态为成功
        ItbBackAdvanceInfo backAdvanceInfo = getItbBackAdvanceInfo(orderInfo, projectInfo);
        itbBackAdvanceInfoRepository.save(backAdvanceInfo);

        // 得到标的出账流水
        ItbCapitalFlowInfo capitalFlowInfoOut = getItbCapitalFlowInfo(orderInfo, backAdvanceInfo, tradeNo);
        itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfoOut);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("返款到垫付公司通知结果成功，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到垫付公司通知结果成功，更新订单表失败。 订单信息：" + orderInfo);
        }
        // 修改标的账户余额
        isPass = itbProjectAccountService.updateBalanceByIdAndVersion(-orderInfo.getAmount(), projectAccount.getId(), projectAccount.getVersion());
        if(!isPass){
            logger.error("返款到垫付公司通知结果成功，更新标的账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到垫付公司通知结果成功，更新标的账户表失败。 订单信息：" + orderInfo);
        }
        // 修改企业账户余额
        isPass = itbCompanyInfoService.updateBalanceByIdAndVersion(orderInfo.getAmount(), companyInfo.getId(), companyInfo.getVersion());
        if(!isPass){
            logger.error("返款到垫付公司通知结果成功，更新企业账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到垫付公司通知结果成功，更新企业账户表失败。 订单信息：" + orderInfo);
        }
        logger.info("支付结果为：{}。 业务处理完成。订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 创建转账记录
     * @param orderInfo
     * @return
     */
    private ItbBackAdvanceInfo getItbBackAdvanceInfo(ItbOrderInfo orderInfo, ItbProjectInfo projectInfo){
        ItbBackAdvanceInfo backAdvanceInfo = itbBackAdvanceInfoRepository.findByProjectIdAndCompanyId(projectInfo.getId(), orderInfo.getUserId());
        ItbRepayInfo repayInfo = itbRepayInfoRepository.findByProjectId(projectInfo.getId());
        if(backAdvanceInfo == null){
            backAdvanceInfo = new ItbBackAdvanceInfo();
            backAdvanceInfo.setLoanUserId(projectInfo.getLoanUserId());
            backAdvanceInfo.setProjectId(projectInfo.getId());
            backAdvanceInfo.setTerm(repayInfo.getTerm());
            backAdvanceInfo.setRepayDate(repayInfo.getRepayTime());
            backAdvanceInfo.setRepayId(repayInfo.getId());
            backAdvanceInfo.setCompanyId(orderInfo.getUserId());
            backAdvanceInfo.setCreateTime(new java.util.Date());

            backAdvanceInfo.setRepayCapital(0d);
            backAdvanceInfo.setRepayInterest(0d);
            backAdvanceInfo.setPenaltyAmount(0d);
        }

        // 剩余返款本金
        double subCapitalAmount = MathUtil.sub(repayInfo.getAdvanceCapital(), backAdvanceInfo.getRepayCapital());
        // 剩余返款收益
        double subRepayInterest = MathUtil.sub(repayInfo.getAdvanceInterest(), backAdvanceInfo.getRepayInterest());
        // 剩余返款违约金
        double subPenaltyAmount = MathUtil.sub(repayInfo.getDuetoReapyPenalty(), backAdvanceInfo.getPenaltyAmount());

        // 返款金额
        double amount = orderInfo.getAmount();

        // 返本金
        if(amount > subCapitalAmount){
            backAdvanceInfo.setRepayCapital(MathUtil.add(backAdvanceInfo.getRepayCapital(), subCapitalAmount));
            amount = MathUtil.sub(amount, subCapitalAmount);
        } else {
            backAdvanceInfo.setRepayCapital(MathUtil.add(backAdvanceInfo.getRepayCapital(), amount));
            amount = 0;
        }

        // 返利息
        if(amount > subRepayInterest){
            backAdvanceInfo.setRepayInterest(MathUtil.add(backAdvanceInfo.getRepayInterest(), subRepayInterest));
            amount =  MathUtil.sub(amount, subRepayInterest);
        } else {
            backAdvanceInfo.setRepayInterest(MathUtil.add(backAdvanceInfo.getRepayInterest(), amount));
            amount = 0;
        }

        // 返违约金
        if(amount > subPenaltyAmount){
            backAdvanceInfo.setPenaltyAmount(MathUtil.add(backAdvanceInfo.getPenaltyAmount(), subPenaltyAmount));
            amount =  MathUtil.sub(amount, subRepayInterest);
        } else {
            backAdvanceInfo.setPenaltyAmount(MathUtil.add(backAdvanceInfo.getPenaltyAmount(), subPenaltyAmount));
            amount = 0;
        }

        // 判断是否返款完成
        if(MathUtil.equals(MathUtil.sub(repayInfo.getAdvanceCapital(), backAdvanceInfo.getRepayCapital()), 0d)
                && MathUtil.equals(MathUtil.sub(repayInfo.getAdvanceInterest(), backAdvanceInfo.getRepayInterest()), 0d)
                && MathUtil.equals(MathUtil.sub(repayInfo.getDuetoReapyPenalty(), backAdvanceInfo.getPenaltyAmount()), 0d)){
            backAdvanceInfo.setStatus(BackAdvanceStatusEnum.SUCCESS.getCode());
        } else {
            backAdvanceInfo.setStatus(BackAdvanceStatusEnum.BACK_IN.getCode());
        }
        return backAdvanceInfo;
    }

    /**
     * 创建流水记录-标的出账
     * @param orderInfo 订单信息
     * @param backAdvanceInfo 垫付返款记录
     * @param tradeNo 第三方流水号
     * @return
     */
    private ItbCapitalFlowInfo getItbCapitalFlowInfo(ItbOrderInfo orderInfo, ItbBackAdvanceInfo backAdvanceInfo, String tradeNo){
        ItbCapitalFlowInfo capitalFlowInfo = new ItbCapitalFlowInfo();
        capitalFlowInfo.setBusinessId(backAdvanceInfo.getId());
        capitalFlowInfo.setBusinessType(orderInfo.getBusinessType());
        capitalFlowInfo.setOrderNo(orderInfo.getOrderNo());
        capitalFlowInfo.setTradeNo(tradeNo);
        capitalFlowInfo.setTransAction(TransferActionEnum.PROJECT_OUT.getCode());
        capitalFlowInfo.setProjectId(orderInfo.getProjectId());
        capitalFlowInfo.setUserId(orderInfo.getUserId());
        capitalFlowInfo.setUserType(orderInfo.getUserType());
        capitalFlowInfo.setAmount(orderInfo.getAmount());
        capitalFlowInfo.setTime(orderInfo.getOrderTime());
        capitalFlowInfo.setCreateTime(new java.util.Date());
        return capitalFlowInfo;
    }
}
