package com.itanbank.account.service.back;

import com.itanbank.account.domain.web.*;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 返款业务接口
 * Created by SDD on 2016/2/26.
 */
public interface ItbBackAmountService {

    /**
     * 返款下订单
     * @param projectInfo
     * @param projectAccount
     * @param investInfo
     * @param ip
     * @return
     */
    ProjectTransferResult addOrderInfo(ItbProjectInfo projectInfo, ItbProjectAccount projectAccount, ItbInvestInfo investInfo, String ip) throws Exception;

    /**
     * 返款业务处理
     * @param orderInfo
     * @param rrojectTransferResult
     * @return
     */
    ExecuetResultCode back(ItbOrderInfo orderInfo, ItbProjectInfo projectInfo, ProjectTransferResult rrojectTransferResult) throws Exception;

    /**
     * 检查并更新标的状态为已完成
     * @param projectInfo
     * @param projectAccount
     * @throws Exception
     */
    public void updateProjectStatus(ItbProjectInfo projectInfo, ItbProjectAccount projectAccount)  throws Exception;
}
