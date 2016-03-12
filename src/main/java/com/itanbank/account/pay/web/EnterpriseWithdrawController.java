package com.itanbank.account.pay.web;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.pay.common.PaySignUtil;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.pay.WithdrawEnterpriseRequest;
import com.itanbank.account.pay.model.pay.WithdrawPersonResult;
import com.itanbank.account.pay.service.PayService;
import com.itanbank.account.service.ItbCompanyInfoService;
import com.itanbank.account.service.ItbOrderInfoService;
import com.itanbank.account.service.ItbWithdrawInfoService;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import org.epbcommons.transformation.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业提现
 * Created by SDD on 2016/2/25.
 */
@Controller
public class EnterpriseWithdrawController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayService payService;

    @Autowired
    private ItbCompanyInfoService itbCompanyInfoService;

    @Autowired
    private ItbOrderInfoService itbOrderInfoService;

    @Autowired
    private ItbWithdrawInfoService itbWithdrawInfoService;

    /**
     * 企业客户提现申请
     * @param request
     * @param companyId 企业ID
     * @param withdrawEnterpriseReq [提现金额:amount]
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/withdraw/enterprise.html")
    public String withdrawEnterprise(HttpServletRequest request, Long companyId, WithdrawEnterpriseRequest withdrawEnterpriseReq) throws Exception{
        logger.info("企业客户提现申请:companyId:{}, withdrawEnterpriseRequest:{}", companyId, withdrawEnterpriseReq);

        // 用户IP地址
        String ip = IpHelper.getIpAddress(request);

        // 获得企业客户提现申请的URL
        request.setAttribute("url", itbOrderInfoService.addEnterpriseWithdrawOrderInfo(companyId, ip, withdrawEnterpriseReq));
        return "pay/forward/request";
    }

    /**
     * 企业客户提现同步通知跳转页面
     * @param view
     * @param withdrawPersonResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/withdraw/enterprise/return.html")
    public ModelAndView withdrawPersonReturn(ModelAndView view,WithdrawPersonResult withdrawPersonResult)throws Exception{
        logger.info("企业客户提现同步通知跳转页面结果:"+withdrawPersonResult.toString());
        view.addObject("msg", withdrawPersonResult.getRet_msg()+"提现金额:"+withdrawPersonResult.getAmount());
        view.setViewName("pay/forward/response");
        return view;
    }

    /**
     * 企业客户提现异步后台通知结果
     * @param request
     * @param withdrawPersonResult
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/withdraw/enterprise/notify.html")
    public String withdrawEnterpriseNotify(HttpServletRequest request,WithdrawPersonResult withdrawPersonResult)throws Exception{
        logger.info("企业客户提现异步后台通知参数:"+withdrawPersonResult.toString());
        boolean verify = PaySignUtil.verify(request);
        logger.info("企业客户提现异步后台通知验签结果：{}", verify);

        ExecuetResultCode resultCode = ExecuetResultCode.E60400;
        if(verify){ // 验签通过
            resultCode = itbWithdrawInfoService.enterpriseWithdraw(withdrawPersonResult);
        }

        // 结果返回
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("sign_type",withdrawPersonResult.getSign_type());
        paramMap.put("mer_id",withdrawPersonResult.getMer_id());
        paramMap.put("version",withdrawPersonResult.getVersion());
        paramMap.put("order_id",withdrawPersonResult.getOrder_id());
        paramMap.put("mer_date",withdrawPersonResult.getMer_date());
        paramMap.put("ret_code", resultCode.getCode());
        String data = Mer2Plat_v40.merNotifyResData(paramMap);
        request.setAttribute("data", data);
        return "pay/forward/notify";
    }
}
