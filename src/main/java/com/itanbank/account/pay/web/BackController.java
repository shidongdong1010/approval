package com.itanbank.account.pay.web;

import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.pay.common.PaySignUtil;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.ServTypeEnum;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import com.itanbank.account.service.ItbOrderInfoService;
import com.itanbank.account.service.ItbProjectInfoService;
import com.itanbank.account.service.back.ItbBackAmountFactory;
import com.itanbank.account.service.back.ItbBackAmountService;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 返款controller
 * Created by SDD on 2016/2/28.
 */
@Controller
public class BackController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItbOrderInfoService itbOrderInfoService;
    @Autowired
    private ItbProjectInfoService itbProjectInfoService;

    /**
     * 返款异步后台通知结果
     * @param request
     * @param projectTransferResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/back/notify.html")
    public String withdrawEnterpriseNotify(HttpServletRequest request, ProjectTransferResult projectTransferResult)throws Exception{
        logger.info("返款异步后台通知参数:" + projectTransferResult.toString());
        boolean verify = PaySignUtil.verify(request);
        logger.info("返款异步后台通知验签结果：{}", verify);

        ExecuetResultCode resultCode = ExecuetResultCode.E60400;
        if(verify){ // 验签通过
            String orderNo = projectTransferResult.getOrder_id();
            ItbOrderInfo orderInfo = itbOrderInfoService.findByOrderNo(orderNo);
            if(orderInfo == null){
                logger.error("返款业务处理失败, 未查询到该订单。 订单号：{}", orderNo);
                resultCode = ExecuetResultCode.E60503;
            }else {
                ItbProjectInfo projectInfo = itbProjectInfoService.findById(orderInfo.getProjectId());

                ItbBackAmountService backService = ItbBackAmountFactory.getBackService(orderInfo.getBusinessType(), orderInfo.getUserType());
                resultCode = backService.back(orderInfo, projectInfo, projectTransferResult);
            }
        }

        // 结果返回
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("sign_type", projectTransferResult.getSign_type());
        paramMap.put("mer_id", projectTransferResult.getMer_id());
        paramMap.put("version", projectTransferResult.getVersion());
        paramMap.put("order_id", projectTransferResult.getOrder_id());
        paramMap.put("mer_date", projectTransferResult.getMer_date());
        paramMap.put("ret_code", resultCode.getCode());
        String data = Mer2Plat_v40.merNotifyResData(paramMap);
        request.setAttribute("data", data);
        return "pay/forward/notify";
    }
}
