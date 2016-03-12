package com.itanbank.account.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.epbcommons.transformation.math.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.common.JsonResult;
import com.itanbank.account.common.JsonResultHelper;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.app.resultcode.UserResultCode;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbRepayInfo;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.project.ProjectTransferNopwdResult;
import com.itanbank.account.service.ItbCompanyInfoService;
import com.itanbank.account.service.ItbRepayInfoService;

/**
 * 还款操作
 * @author wp
 *
 */
@Controller
public class ItbRepayInfoController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItbRepayInfoService  itbRepayInfoService;
	
	@Autowired
	private JsonResultHelper jsonResultHelper;
	
	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;
	
	@RequestMapping("/repayMentList.html")
	public  String  repayMentList(){
		return "/loanManageMent/repayMentList";
	}
	/**
	 * 还款信息查询
	 * 
	 * @return
	 */
	@RequestMapping("/repayMentList.json")
	@ResponseBody
	public JqGridPage repayMentList(ItbRepayInfo itbRepayInfo, HttpServletRequest request) {
		logger.info("查询还款信息...");
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbRepayInfo> pages = itbRepayInfoService.findPage(itbRepayInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	
	@RequestMapping("/transfer/repayMentQueryList.html")
	public  String  repayMentQueryList(HttpServletRequest request){
		request.setAttribute("reqProjectId",request.getParameter("reqProjectId"));
		return "/transfer/repayMentQueryList";
	}
	/**
	 * 还款信息查询(数据管理)
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/repayMentQueryList.json")
	@ResponseBody
	public JqGridPage repayMentQueryList(ItbRepayInfo itbRepayInfo, HttpServletRequest request) {
		logger.info("查询还款信息...");
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbRepayInfo> pages = itbRepayInfoService.findQueryPage(itbRepayInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 还款操作
	 * @param request
	 * @param id
	 * @param repayAmount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doRepayMent.json")
	@ResponseBody
	public JsonResult  doRepayMent(HttpServletRequest request,Long id,String repayAmount)throws Exception{
		logger.info("还款申请，还款ID："+id);
		//调用第三方还款接口
        String ip = IpHelper.getIpAddress(request);
        if(null != id && !StringUtils.isEmpty(repayAmount)){
        	ProjectTransferNopwdResult projectTransferNopwdResult= itbRepayInfoService.repayInfoRequest(id, Double.parseDouble(repayAmount), ip);
        	if(ExecuetResultCode.SCUESS.getCode().equals(projectTransferNopwdResult.getRet_code())){
        		return jsonResultHelper.buildSuccessJsonResult(null);
        	}else{
        		return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
        	}
        }else{
        	logger.error("还款申请失败,数据不完整..");
        	return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
        }
	}
	
	/**
	 * 查询企业账户余额，应还金额
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/repay/queryCompanyAmount.json")
	@ResponseBody
	public JsonResult  queryCompanyAmount(Long id)throws Exception{
		logger.info("查询企业账户余额,企业ID："+id);
		if(null == id){
			return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
		}else{
			ItbRepayInfo  itbRepayInfo = itbRepayInfoService.findById(id);
			ItbCompanyInfo  itbCompanyInfo = itbCompanyInfoService.findById(itbRepayInfo.getLoanUserId());
			Double balance = itbCompanyInfoService.queryCompanyAmount(itbCompanyInfo.getId());//企业账户余额
			Double   sumDuetoAmount =MathUtil.add(MathUtil.add(itbRepayInfo.getDuetoReapyCapital()== null ?0.0:itbRepayInfo.getDuetoReapyCapital(),itbRepayInfo.getDuetoReapyInterest()==null?0.0:itbRepayInfo.getDuetoReapyInterest()), MathUtil.add(itbRepayInfo.getDuetoReapyPenalty()==null?0.0:itbRepayInfo.getDuetoReapyPenalty(),itbRepayInfo.getDuetoLateRepayServerAmount()==null?0.0:itbRepayInfo.getDuetoLateRepayServerAmount()));
			Double   sumPaidAmount = MathUtil.add(MathUtil.add(itbRepayInfo.getPaidReapyCapital()==null?0.0:itbRepayInfo.getPaidReapyCapital(),itbRepayInfo.getPaidReapyInterest()==null?0.0:itbRepayInfo.getPaidReapyInterest()),MathUtil.add(itbRepayInfo.getPaidReapyPenalty()==null?0.0:itbRepayInfo.getPaidReapyPenalty(),itbRepayInfo.getPaidLateRepayServerAmount()==null?0.0:itbRepayInfo.getPaidLateRepayServerAmount()));
			Map<String,Object>  dataMap = new HashMap<String,Object>();
			dataMap.put("id", itbRepayInfo.getId());
			dataMap.put("sumDuetoAmount", sumDuetoAmount);//应还金额
			dataMap.put("sumPaidAmount", sumPaidAmount);//已还金额
			dataMap.put("balance", balance);//第三方账户余额
			dataMap.put("localBalance", itbCompanyInfo.getBalance());//本地账户余额
			if(balance > MathUtil.sub(sumDuetoAmount, sumPaidAmount)){
				//第三方账户大于应还金额
				dataMap.put("repayMentAmount",MathUtil.sub(sumDuetoAmount, sumPaidAmount));
			}else{
				//第三方账户小于应还金额
				dataMap.put("repayMentAmount",balance);
			}
			return jsonResultHelper.buildSuccessJsonResult(dataMap);
		}
	}
}
