package com.itanbank.account.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.common.JsonResultHelper;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.app.resultcode.UserResultCode;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.service.ItbCompanyInfoService;

@Controller
public class ItbCompanyInfoController {
	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;
	
	@Autowired
	private JsonResultHelper  jsonResultHelper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/companyInfoList.html")
	public  String  companyInfoList(){
		return "/loanManageMent/companyInfoList";
	}
	
	/**
	 * 企业账户列表页
	 * 
	 * @return
	 */
	@RequestMapping("/companyInfoList.json")
	@ResponseBody
	public JqGridPage companyList(ItbCompanyInfo itbCompanyInfo, HttpServletRequest request) {
		logger.info("查询企业账户信息...");
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbCompanyInfo> pages = itbCompanyInfoService.findPage(itbCompanyInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 查询企业账户余额
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryCompanyAmount.json")
	@ResponseBody
	public JsonResult  queryCompanyAmount(Long id)throws Exception{
		logger.info("查询企业账户余额,企业ID："+id);
		if(null == id){
			return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
		}else{
			Double balance = itbCompanyInfoService.queryCompanyAmount(id);
			return jsonResultHelper.buildSuccessJsonResult(balance);
		}
	}
}
