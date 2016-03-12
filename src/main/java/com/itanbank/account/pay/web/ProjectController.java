package com.itanbank.account.pay.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.pay.common.PaySignUtil;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.BasicModel;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdResult;
import com.itanbank.account.pay.model.project.ProjectTransferResult;
import com.itanbank.account.service.ItbProjectInfoService;
import com.itanbank.account.service.ItbRepayInfoService;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

/**
 * 标的操作
 * @author wn
 *
 */
@Controller
@RequestMapping("/pay/project")
public class ProjectController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ItbProjectInfoService itbProjectInfoService;
	@Autowired
	private ItbRepayInfoService  itbRepayInfoService;
	/**
	 * 无密标的转入结果通知--满标
	 * @param view
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transferNopwdNotify/fullProject.html")
	public ModelAndView transferNopwdNotifyFullProject(HttpServletRequest request,ModelAndView view,ProjectTransferNopwdResult projectTransferResult) throws Exception{
		logger.info("无密标的转入结果通知--满标："+projectTransferResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("无密标的转入结果通知--满标验签结果：{}", verify);
		JsonResult result = new JsonResult(Integer.parseInt(ExecuetResultCode.E60400.getCode()),ExecuetResultCode.E60400.getDesc());
		if(verify){ // 验签通过
			result = itbProjectInfoService.fullProjectResult(projectTransferResult);
		}
		if(200 !=result.getCode()){
			view.addObject("data", "系统异常");
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sign_type",projectTransferResult.getSign_type());
			paramMap.put("mer_id",projectTransferResult.getMer_id());
			paramMap.put("version",projectTransferResult.getVersion());
			paramMap.put("order_id",projectTransferResult.getOrder_id());
			paramMap.put("mer_date",projectTransferResult.getMer_date());
			paramMap.put("ret_code",BasicModel.RET_CODE_SUCCESS);
			String data = Mer2Plat_v40.merNotifyResData(paramMap);
			view.addObject("data", data);
		}
		view.setViewName("pay/forward/notify");
		return view;
	}
	/**
	 * 标的转账通知--放款
	 * @param view
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transferNotify/pay.html")
	public ModelAndView transferNotifyPay(HttpServletRequest request,ModelAndView view,ProjectTransferResult projectTransferResult) throws Exception{
		logger.info("标的转账通知--放款："+projectTransferResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("标的转账通知--放款验签结果：{}", verify);
		JsonResult result = new JsonResult(Integer.parseInt(ExecuetResultCode.E60400.getCode()),ExecuetResultCode.E60400.getDesc());
		if(verify){ // 验签通过
			result = itbProjectInfoService.payResult(projectTransferResult);
		}
		if(200 !=result.getCode()){
			view.addObject("data", "系统异常");
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sign_type",projectTransferResult.getSign_type());
			paramMap.put("mer_id",projectTransferResult.getMer_id());
			paramMap.put("version",projectTransferResult.getVersion());
			paramMap.put("order_id",projectTransferResult.getOrder_id());
			paramMap.put("mer_date",projectTransferResult.getMer_date());
			paramMap.put("ret_code",BasicModel.RET_CODE_SUCCESS);
			String data = Mer2Plat_v40.merNotifyResData(paramMap);
			view.addObject("data", data);
		}
		view.setViewName("pay/forward/notify");
		return view;
	} 
	
	/**
	 * 标的还款通知--还款
	 * @param view
	 * @param projectTransferNopwdResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transferNotify/rePayMent.html")
	public ModelAndView transferNotifyRePayMent(HttpServletRequest request,ModelAndView view,ProjectTransferNopwdResult projectTransferNopwdResult) throws Exception{
		logger.info("标的还款通知--还款："+projectTransferNopwdResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("标的还款通知--还款验签结果：{}", verify);
		JsonResult result = new JsonResult(Integer.parseInt(ExecuetResultCode.E60400.getCode()),ExecuetResultCode.E60400.getDesc());
		if(verify){ // 验签通过
			result = itbRepayInfoService.repayInfoResponse(projectTransferNopwdResult);
		}
		if(200 !=result.getCode()){
			view.addObject("data", "系统异常");
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sign_type",projectTransferNopwdResult.getSign_type());
			paramMap.put("mer_id",projectTransferNopwdResult.getMer_id());
			paramMap.put("version",projectTransferNopwdResult.getVersion());
			paramMap.put("order_id",projectTransferNopwdResult.getOrder_id());
			paramMap.put("mer_date",projectTransferNopwdResult.getMer_date());
			paramMap.put("ret_code",BasicModel.RET_CODE_SUCCESS);
			String data = Mer2Plat_v40.merNotifyResData(paramMap);
			view.addObject("data", data);
		}
		view.setViewName("pay/forward/notify");
		return view;
	} 
	
	/**
	 * 标的转账通知--平台收费
	 * @param view
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transferNotify/charge.html")
	public ModelAndView transferNotifyCharge(HttpServletRequest request,ModelAndView view,ProjectTransferResult projectTransferResult) throws Exception{
		logger.info("标的转账通知--平台收费："+projectTransferResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("标的转账通知--平台收费验签结果：{}", verify);
		JsonResult result = new JsonResult(Integer.parseInt(ExecuetResultCode.E60400.getCode()),ExecuetResultCode.E60400.getDesc());
		if(verify){ // 验签通过
			result = itbProjectInfoService.chargeResult(projectTransferResult);
		}
		if(200 !=result.getCode()){
			view.addObject("data", "系统异常");
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sign_type",projectTransferResult.getSign_type());
			paramMap.put("mer_id",projectTransferResult.getMer_id());
			paramMap.put("version",projectTransferResult.getVersion());
			paramMap.put("order_id",projectTransferResult.getOrder_id());
			paramMap.put("mer_date",projectTransferResult.getMer_date());
			paramMap.put("ret_code",BasicModel.RET_CODE_SUCCESS);
			String data = Mer2Plat_v40.merNotifyResData(paramMap);
			view.addObject("data", data);
		}
		view.setViewName("pay/forward/notify");
		return view;
	} 
	/**
	 * 标的转账通知--偿付
	 * @param view
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transferNotify/advance.html")
	public ModelAndView transferNotifyAdvance(HttpServletRequest request,ModelAndView view,ProjectTransferResult projectTransferResult) throws Exception{
		logger.info("标的转账通知--偿付："+projectTransferResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("标的转账通知--偿付验签结果：{}", verify);
		JsonResult result = new JsonResult(Integer.parseInt(ExecuetResultCode.E60400.getCode()),ExecuetResultCode.E60400.getDesc());
		if(verify){ // 验签通过
			result = itbProjectInfoService.advanceResult(projectTransferResult);
		}
		if(200 !=result.getCode()){
			view.addObject("data", "系统异常");
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sign_type",projectTransferResult.getSign_type());
			paramMap.put("mer_id",projectTransferResult.getMer_id());
			paramMap.put("version",projectTransferResult.getVersion());
			paramMap.put("order_id",projectTransferResult.getOrder_id());
			paramMap.put("mer_date",projectTransferResult.getMer_date());
			paramMap.put("ret_code",BasicModel.RET_CODE_SUCCESS);
			String data = Mer2Plat_v40.merNotifyResData(paramMap);
			view.addObject("data", data);
		}
		view.setViewName("pay/forward/notify");
		return view;
	} 
	/**
	 * 标的转账通知--贴现
	 * @param request
	 * @param view
	 * @param projectTransferResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transferNotify/discount.html")
	public ModelAndView transferNotifyDiscount(HttpServletRequest request,ModelAndView view,ProjectTransferResult projectTransferResult) throws Exception{
		logger.info("标的转账通知--贴现："+projectTransferResult.toString());
		boolean verify = PaySignUtil.verify(request);
		logger.info("标的转账通知--贴现验签结果：{}", verify);
		JsonResult result = new JsonResult(Integer.parseInt(ExecuetResultCode.E60400.getCode()),ExecuetResultCode.E60400.getDesc());
		if(verify){ // 验签通过
			result = itbProjectInfoService.discountResult(projectTransferResult);
		}
		if(200 !=result.getCode()){
			view.addObject("data", "系统异常");
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sign_type",projectTransferResult.getSign_type());
			paramMap.put("mer_id",projectTransferResult.getMer_id());
			paramMap.put("version",projectTransferResult.getVersion());
			paramMap.put("order_id",projectTransferResult.getOrder_id());
			paramMap.put("mer_date",projectTransferResult.getMer_date());
			paramMap.put("ret_code",BasicModel.RET_CODE_SUCCESS);
			String data = Mer2Plat_v40.merNotifyResData(paramMap);
			view.addObject("data", data);
		}
		view.setViewName("pay/forward/notify");
		return view;
	}
}
