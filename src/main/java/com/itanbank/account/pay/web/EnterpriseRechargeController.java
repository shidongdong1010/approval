package com.itanbank.account.pay.web;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.pay.common.PaySignUtil;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.pay.*;
import com.itanbank.account.pay.service.PayService;
import com.itanbank.account.service.ItbCompanyInfoService;
import com.itanbank.account.service.ItbOrderInfoService;
import com.itanbank.account.service.ItbRechargeInfoService;
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
 * 企业充值接口
 * @author wn
 *
 */
@Controller
public class EnterpriseRechargeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PayService payService;

	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;

	@Autowired
	private ItbOrderInfoService itbOrderInfoService;

	@Autowired
	private ItbRechargeInfoService itbRechargeInfoService;

	/**
	 * 企业客户充值申请
	 * @param request
	 * @param companyId 企业ID
	 * @param rechargeEnterpriseReq [支付方式:pay_type, 充值金额:amount]
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pay/recharge/enterprise.html")
	public String rechargeEnterprise(HttpServletRequest request, Long companyId, RechargeEnterpriseRequest rechargeEnterpriseReq) throws Exception{
		logger.info("企业客户充值申请:companyId:{}, rechargeEnterpriseRequest:{}", companyId, rechargeEnterpriseReq);
		// 用户IP地址
		String ip = IpHelper.getIpAddress(request);

		// 获得企业客户充值申请的URL
		request.setAttribute("url", itbOrderInfoService.addEnterpriseRechargeOrderInfo(companyId, ip, rechargeEnterpriseReq));
		return "pay/forward/request";
	}

	/**
	 * 企业客户充值同步通知跳转页面
	 * @param view
	 * @param rechargePersonResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pay/recharge/enterprise/return.html")
	public ModelAndView rechargePersonReturn(ModelAndView view,RechargePersonResult rechargePersonResult)throws Exception{
		logger.info("企业客户充值同步通知跳转页面结果:"+rechargePersonResult.toString());
		view.addObject("msg", rechargePersonResult.getRet_msg()+"账户余额:"+rechargePersonResult.getBalance());
		view.setViewName("pay/forward/response");
		return view;
	}

	/**
	 * 企业客户充值异步后台通知结果
	 * @param request
	 * @param rechargePersonResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pay/recharge/enterprise/notify.html")
	public String rechargeEnterpriseNotify(HttpServletRequest request,RechargePersonResult rechargePersonResult)throws Exception{
		logger.info("企业客户充值异步后台通知参数:"+rechargePersonResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("企业客户充值异步后台通知验签结果：{}", verify);

		ExecuetResultCode resultCode = ExecuetResultCode.E60400;
		if(verify){ // 验签通过
			resultCode = itbRechargeInfoService.enterpriseRecharge(rechargePersonResult);
		}

		// 结果返回
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sign_type",rechargePersonResult.getSign_type());
		paramMap.put("mer_id",rechargePersonResult.getMer_id());
		paramMap.put("version",rechargePersonResult.getVersion());
		paramMap.put("order_id",rechargePersonResult.getOrder_id());
		paramMap.put("mer_date",rechargePersonResult.getMer_date());
		paramMap.put("ret_code", resultCode.getCode());
		String data = Mer2Plat_v40.merNotifyResData(paramMap);
		request.setAttribute("data", data);
		return "pay/forward/notify";
	}

}
