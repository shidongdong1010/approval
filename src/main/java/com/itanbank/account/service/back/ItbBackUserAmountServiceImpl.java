package com.itanbank.account.service.back;

import com.itanbank.account.domain.app.enums.YesOrNoEnum;
import com.itanbank.account.domain.web.*;
import com.itanbank.account.domain.web.enums.*;
import com.itanbank.account.pay.enums.*;
import com.itanbank.account.pay.model.BasicModel;
import com.itanbank.account.pay.model.project.ProjectTransferRequest;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import com.itanbank.account.pay.model.project.ProjectUpdateRequest;
import com.itanbank.account.pay.model.project.ProjectUpdateResult;
import com.itanbank.account.pay.service.ProjectService;
import com.itanbank.account.repository.web.ItbBackUserInfoRepository;
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
 * 返款到个人投资者
 * Created by SDD on 2016/2/26.
 */
@Transactional
@Service
public class ItbBackUserAmountServiceImpl implements ItbBackAmountService {

    @Autowired
    private OrderNoService orderNoService;

    @Autowired
    private ItbOrderInfoRepository itbOrderInfoRepository;

    @Autowired
    private ItbOrderInfoService itbOrderInfoService;

    @Autowired
    private ItbUserAccountService itbUserAccountService;

    @Autowired
    private ItbCapitalFlowService itbCapitalFlowService;

    @Autowired
    private ItbProjectInfoService itbProjectInfoService;

    @Autowired
    private ItbProjectAccountService itbProjectAccountService;

    @Autowired
    private ItbRepayInfoRepository itbRepayInfoRepository;

    @Autowired
    private ItbInvestService itbInvestService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ItbBackUserInfoRepository itbBackUserInfoRepository;

    @Autowired
    private ItbUserService itbUserService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 检查是否还可以返款
     * @param userId 用户ID
     * @param projectId 标的ID
     * @param investInfo 投资记录
     * @param amount 本次返款金额
     * @return
     */
    private boolean isBack(Long userId, Long projectId, ItbInvestInfo investInfo, double amount){
        // 查询个人投资者的支付中或支付完成的订单
        List<ItbOrderInfo> list = itbOrderInfoRepository.findPayInByUser(userId, projectId, BusinessTypeEnum.REPAY_BACK.getCode());
        if(list != null) {

            // 返款记录订单之后(完成的订单或支付中的订单)
            double amountSum = 0d;
            for(ItbOrderInfo orderInfo : list) {
                if(orderInfo.getRemarks().equals(investInfo.getId().toString())) {
                    amountSum = MathUtil.add(amountSum, orderInfo.getAmount());
                }
            }
            amountSum = MathUtil.add(amountSum, amount);

            // 本金+预期收益
            double investAmountSum =  MathUtil.add(investInfo.getAmount(), investInfo.getReturnAmount());

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
     * @param investInfo
     * @param ip
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    @Override
    public ProjectTransferResult addOrderInfo(ItbProjectInfo projectInfo, ItbProjectAccount projectAccount, ItbInvestInfo investInfo, String ip) throws Exception{
        ItbUser user = itbUserService.findById(investInfo.getUserId());

        // 请求参数金额为元
        Double amount = MathUtil.add(investInfo.getAmount(), investInfo.getReturnAmount()); // 投资本金 + 收益
        // 查询返款记录, 将已经返款的金额减去
        ItbBackUserInfo backUserInfo = itbBackUserInfoRepository.findByInvestId(investInfo.getId());
        if(backUserInfo != null){
            amount = MathUtil.sub(MathUtil.sub(amount, backUserInfo.getRepayCapital()), backUserInfo.getReturnAmount());
        }

        if(amount <= 0){
            logger.info("投资人{}, 返款申请已经返款完成，本次无需操作", investInfo.getUserName());
            return null;
        }

        // 检查是否已经回款
        if (this.isBack(user.getId(), projectInfo.getId(), investInfo, amount)){
            logger.info("投资人{}, 返款申请已经处理，本次无需重复操作", investInfo.getUserName());
            return null;
        }

        // 获得订单号
        String orderNo = orderNoService.getOrderNo(BusinessTypeEnum.REPAY_BACK.getCode());

        ItbOrderInfo orderInfo = new ItbOrderInfo();
        orderInfo.setOrderNo(orderNo);	// 订单号
        orderInfo.setOrderStatus(OrderStatusEnum.PAYMENT.getCode()); // 订单状态为支付中
        orderInfo.setBusinessType(BusinessTypeEnum.REPAY_BACK.getCode()); // 业务类型为还款后返款
        orderInfo.setAmount(amount); // 金额
        orderInfo.setOrderDesc(BusinessTypeEnum.REPAY_BACK.getDesc());  // 订单描述
        orderInfo.setUserId(user.getId());
        orderInfo.setUserType(UserTypeEnum.PERSON.getCode());
        orderInfo.setUserName(user.getRealName());
        orderInfo.setUserMobile(user.getMobile());

        orderInfo.setAppId(projectInfo.getAppId());
        orderInfo.setLoanContractNo(projectInfo.getLoanContractNo());
        orderInfo.setProjectId(projectInfo.getId());

        orderInfo.setOrderTime(new java.util.Date()); // 订单时间
        orderInfo.setInPayNo(user.getPayId()); // 用户第三方支付帐户号
        orderInfo.setOutPayNo(projectAccount.getPayProjectId());    // 转出方第三方支付账号

        orderInfo.setUserIp(ip);    // 用户IP
        orderInfo.setCreateTime(new java.util.Date()); // 创建时间
        orderInfo.setVersion(0L); // 版本号
        orderInfo.setRemarks(investInfo.getId()+""); // 将投资的ID放入到备注字段
        itbOrderInfoRepository.save(orderInfo);
        logger.info("返款到个人投资者, 保存订单{}", orderInfo);

        ProjectTransferRequest projectTransferRequest = new ProjectTransferRequest();
        // 设置订单日期
        projectTransferRequest.setMer_date(new SimpleDateFormat("yyyyMMdd").format(orderInfo.getOrderTime()));
        // 设置订单号
        projectTransferRequest.setOrder_id(orderInfo.getOrderNo());
        projectTransferRequest.setRet_url(BasicModel.NOTIFY_SERVER+"pay/back/notify1.html");
        projectTransferRequest.setNotify_url(BasicModel.NOTIFY_SERVER+"pay/back/notify.html");
        projectTransferRequest.setProject_id(projectAccount.getPayProjectId()); // 第三方标的ID
        //projectTransferRequest.setProject_account_id(projectAccount.getPayProjectNo()); // 第三方标的号
        projectTransferRequest.setServ_type(ServTypeEnum.REPAY_BACK.getCode()); // 业务类型：还款后返款
        projectTransferRequest.setTrans_action(TransActionEnum.OUT.getCode()); // 转账方向：转出
        projectTransferRequest.setPartic_type(ParticTypeEnum.INVESTOR.getCode()); // 转账方类型: 借款人
        projectTransferRequest.setPartic_acc_type(ParticAccTypeEnum.PERSON.getCode()); // 转账方账户类型 02 商户
        projectTransferRequest.setPartic_user_id(user.getPayId());
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
     * 检查并更新标的状态为已完成
     * @param projectInfo
     * @param projectAccount
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void updateProjectStatus(ItbProjectInfo projectInfo, ItbProjectAccount projectAccount)  throws Exception{
        // 检查是否已经全部返款完成(投资记录与返款记录一一对应)
        // 投资记录数
        List<ItbInvestInfo> investInfos = itbInvestService.findByProjectIdAndStatus(projectInfo.getId(), ItbInvestStatusEnum.FINISH.getCode());
        Double duetoBackAmount = 0d;
        for(ItbInvestInfo investInfo : investInfos){
            duetoBackAmount = MathUtil.add(MathUtil.add(duetoBackAmount, investInfo.getAmount()), investInfo.getReturnAmount());
        }
        // 返款记录数
        List<ItbBackUserInfo> backUserInfos = itbBackUserInfoRepository.findByProjectId(projectInfo.getId());
        Double paidBackAmount = 0d;
        for (ItbBackUserInfo itbBackUserInfo : backUserInfos){
            paidBackAmount = MathUtil.add(MathUtil.add(paidBackAmount, itbBackUserInfo.getReturnAmount()), itbBackUserInfo.getRepayCapital());
        }

        logger.info("检查标的是否已经返款完成: 应返款金额{}, 已返款金额{}", duetoBackAmount, paidBackAmount);
        if(MathUtil.equals(duetoBackAmount, paidBackAmount)){ // 说明返款完成
            if(String.valueOf(YesOrNoEnum.N.getCode()).equals(projectAccount.getIsAdvance()) || projectAccount.getIsAdvance() == null){ // 是否垫付
                // 更新为已经完成
                if(updateProjectStatus(projectAccount)) {
                    projectInfo.setStatus(ProjectStatusEnum.END.getCode()); // 已还完
                    itbProjectInfoService.saveItbProjectInfo(projectInfo);
                }
            } else {
                projectInfo.setStatus(ProjectStatusEnum.LATEREPAY.getCode()); // 逾期中
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
     * 返款到个人投资者，业务处理
     * @param orderInfo
     * @param rojectTransferResult
     * @return
     */
    @Override
    public ExecuetResultCode back(ItbOrderInfo orderInfo, ItbProjectInfo projectInfo, ProjectTransferResult rojectTransferResult) throws Exception{
        String orderNo = rojectTransferResult.getOrder_id();  // 订单号
        String tradeNo = rojectTransferResult.getTrade_no();  // 第三方交易流水号

        ItbUserAccount userAccount = itbUserAccountService.findById(orderInfo.getUserId()); // 查询个人投资者用户账户
        ItbProjectAccount projectAccount = itbProjectAccountService.findById(orderInfo.getProjectId()); // 查询标的账户

        // 验证订单状态是否为支付中
        if(OrderStatusEnum.PAYMENT.equals(orderInfo.getOrderStatus())) {
            logger.error("返款到个人投资者业务处理失败, 订单目前状态不是支付中。 订单信息：{}", orderInfo);
            return ExecuetResultCode.E60781;
        }
        // 处理返回结果
        ExecuetResultCode resultCode = ExecuetResultCode.getByCode(rojectTransferResult.getRet_code());
        if(ExecuetResultCode.SCUESS.equals(resultCode)){ // 支付成功
            this.backSuccess(orderInfo, tradeNo, projectInfo, userAccount, projectAccount, rojectTransferResult.getRet_msg());
        }else { // 支付失败
            this.backFailure(orderInfo, tradeNo, projectInfo, rojectTransferResult.getRet_msg());
        }
        return ExecuetResultCode.SCUESS;
    }

    /**
     * 返款到个人投资者 - 失败业务处理
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
            logger.error("返款到个人投资者通知结果失败，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到个人投资者通知结果失败，更新订单表失败。 订单信息：" + orderInfo);
        }
        logger.info("返款到个人投资者通知结果失败，业务处理完成。 失败原因：{}， 订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 返款到个人投资者 - 成功业务处理
     * @param orderInfo
     * @param tradeNo
     * @param userAccount
     * @param retMsg
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    private void backSuccess(ItbOrderInfo orderInfo, String tradeNo, ItbProjectInfo projectInfo, ItbUserAccount userAccount, ItbProjectAccount projectAccount , String retMsg) throws Exception{
        // 生成返款记录 - 状态为成功
        ItbBackUserInfo backUserInfo = getItbBackUserInfo(orderInfo, projectInfo);
        itbBackUserInfoRepository.save(backUserInfo);

        // 得到标的出账流水
        ItbCapitalFlowInfo capitalFlowInfoOut = getItbCapitalFlowInfo(orderInfo, backUserInfo, tradeNo);
        itbCapitalFlowService.saveItbCapitalFlow(capitalFlowInfoOut);

        // 修改订单状态为支付完成
        String orderStatus = OrderStatusEnum.COMPLETE_PAYMENT.getCode();
        boolean isPass = itbOrderInfoService.updateOrderByIdAndVersion(tradeNo, orderStatus, orderInfo.getId(), orderInfo.getVersion());
        if(!isPass){
            logger.error("返款到个人投资者通知结果成功，更新订单表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到个人投资者通知结果成功，更新订单表失败。 订单信息：" + orderInfo);
        }
        // 修改标的账户余额
        isPass = itbProjectAccountService.updateBalanceByIdAndVersion(-orderInfo.getAmount(), projectAccount.getId(), projectAccount.getVersion());
        if(!isPass){
            logger.error("返款到个人投资者通知结果成功，更新标的账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到个人投资者通知结果成功，更新标的账户表失败。 订单信息：" + orderInfo);
        }
        // 修改用户账户余额
        isPass = itbUserAccountService.updateBalanceByIdAndVersion(orderInfo.getAmount(), userAccount.getId(), userAccount.getVersion());
        if(!isPass){
            logger.error("返款到个人投资者通知结果成功，更新用户账户表失败。 订单信息：{}", orderInfo);
            throw new Exception("返款到个人投资者通知结果成功，更新用户账户表失败。 订单信息：" + orderInfo);
        }
        logger.info("支付结果为：{}。 业务处理完成。订单信息：{}", retMsg, orderInfo);
    }

    /**
     * 创建转账记录
     * @param orderInfo
     * @return
     */
    private ItbBackUserInfo getItbBackUserInfo(ItbOrderInfo orderInfo, ItbProjectInfo projectInfo){
        // 查询投资记录
        ItbInvestInfo investInfo = itbInvestService.findById(new Long(orderInfo.getRemarks()));
        // 查询返款记录
        ItbBackUserInfo backUserInfo = itbBackUserInfoRepository.findByInvestId(investInfo.getId());
        if(backUserInfo == null){
            // 查询还款记录
            ItbRepayInfo repayInfo = itbRepayInfoRepository.findByProjectId(projectInfo.getId());

            backUserInfo = new ItbBackUserInfo();
            backUserInfo.setInvestId(investInfo.getId());
            backUserInfo.setLoanUserId(projectInfo.getLoanUserId());
            backUserInfo.setProjectId(projectInfo.getId());
            backUserInfo.setTerm(repayInfo.getTerm());
            backUserInfo.setRepayDate(repayInfo.getRepayTime());
            backUserInfo.setRepayId(repayInfo.getId());
            backUserInfo.setUserId(orderInfo.getUserId());
            backUserInfo.setUserType(UserTypeEnum.PERSON.getCode());
            backUserInfo.setCreateTime(new java.util.Date());

            backUserInfo.setRepayCapital(0d);
            backUserInfo.setReturnAmount(0d);
        }

        // 剩余返款本金
        double subCapitalAmount = MathUtil.sub(investInfo.getAmount(), backUserInfo.getRepayCapital());
        // 剩余返款收益
        double subReturnAmount = MathUtil.sub(investInfo.getReturnAmount(), backUserInfo.getReturnAmount());

        // 返款金额
        double amount = orderInfo.getAmount();
        // 返款到本金还少金额
        double subReturnCapitalAmount = MathUtil.sub(subCapitalAmount, amount);

        // 返本金
        if(amount > subCapitalAmount){
            backUserInfo.setRepayCapital(MathUtil.add(backUserInfo.getRepayCapital(), subCapitalAmount));
            amount = MathUtil.sub(amount, subCapitalAmount);
        } else {
            backUserInfo.setRepayCapital(MathUtil.add(backUserInfo.getRepayCapital(), amount));
            amount = 0;
        }
        // 返收益
        if(amount > subReturnAmount){
            backUserInfo.setReturnAmount(MathUtil.add(backUserInfo.getReturnAmount(), subReturnAmount));
            amount =  MathUtil.sub(amount, subReturnAmount);;
        } else {
            backUserInfo.setReturnAmount(MathUtil.add(backUserInfo.getReturnAmount(), amount));
            amount = 0;
        }

        // 判断是否返款完成
        if(MathUtil.equals(MathUtil.sub(investInfo.getAmount(), backUserInfo.getRepayCapital()), 0d)
                && MathUtil.equals(MathUtil.sub(investInfo.getReturnAmount(), backUserInfo.getReturnAmount()), 0d)){
            backUserInfo.setStatus(BackUserStatusEnum.SUCCESS.getCode());
        } else {
            backUserInfo.setStatus(BackUserStatusEnum.BACK_IN.getCode());
        }
        return backUserInfo;
    }

    /**
     * 创建流水记录-标的出账
     * @param orderInfo 订单信息
     * @param backUserInfo 返款记录
     * @param tradeNo 第三方流水号
     * @return
     */
    private ItbCapitalFlowInfo getItbCapitalFlowInfo(ItbOrderInfo orderInfo, ItbBackUserInfo backUserInfo, String tradeNo){
        ItbCapitalFlowInfo capitalFlowInfo = new ItbCapitalFlowInfo();
        capitalFlowInfo.setBusinessId(backUserInfo.getId());
        capitalFlowInfo.setBusinessType(orderInfo.getBusinessType());
        capitalFlowInfo.setOrderNo(orderInfo.getOrderNo());
        capitalFlowInfo.setTradeNo(tradeNo);
        capitalFlowInfo.setTransAction(TransferActionEnum.PROJECT_OUT.getCode());
        capitalFlowInfo.setProjectId(orderInfo.getProjectId());
        capitalFlowInfo.setUserId(orderInfo.getUserId());
        capitalFlowInfo.setUserType(UserTypeEnum.PERSON.getCode());
        capitalFlowInfo.setAmount(orderInfo.getAmount());
        capitalFlowInfo.setTime(orderInfo.getOrderTime());
        capitalFlowInfo.setCreateTime(new java.util.Date());
        return capitalFlowInfo;
    }
}
