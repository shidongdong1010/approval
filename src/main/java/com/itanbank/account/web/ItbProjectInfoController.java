package com.itanbank.account.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.itanbank.account.common.IpHelper;
import com.itanbank.account.common.JsonResult;
import com.itanbank.account.common.JsonResultHelper;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.web.ItbCompanyInfo;
import com.itanbank.account.domain.web.ItbProjectAccount;
import com.itanbank.account.domain.web.ItbProjectInfo;
import com.itanbank.account.domain.web.VItbProjectAll;
import com.itanbank.account.domain.web.enums.CompanyStatusEnum;
import com.itanbank.account.domain.web.enums.CompanyTypeEnum;
import com.itanbank.account.domain.web.enums.ExceptionResultCode;
import com.itanbank.account.domain.web.enums.ProjectStatusEnum;
import com.itanbank.account.pay.model.query.EnterpriseRequest;
import com.itanbank.account.pay.model.query.EnterpriseResult;
import com.itanbank.account.pay.model.query.ProjectRequest;
import com.itanbank.account.pay.model.query.ProjectResult;
import com.itanbank.account.pay.service.QueryPtpMerService;
import com.itanbank.account.pay.service.QueryService;
import com.itanbank.account.service.ItbCompanyInfoService;
import com.itanbank.account.service.ItbProjectAccountService;
import com.itanbank.account.service.ItbProjectInfoService;
import com.itanbank.account.service.VItbProjectAllService;

/**
 * 标的操作
 * @author wn
 *
 */
@Controller
public class ItbProjectInfoController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItbProjectInfoService itbProjectInfoService;

	@Autowired
	private VItbProjectAllService vItbProjectAllService;

	@Autowired
	private ItbProjectAccountService itbProjectAccountService;
	
	@Autowired
	private ItbCompanyInfoService itbCompanyInfoService;
	
	@Autowired
	private JsonResultHelper jsonResultHelper;

	@Autowired
	private QueryService queryService;
	
	@Autowired
	private QueryPtpMerService queryPtpMerService;
	
	
	@RequestMapping("/loanList.html")
	public  String  loanList(){
		return "/loanManageMent/loanList";
	}
	/**
	 * 待放款标的信息列表页
	 * 
	 * @return
	 */
	@RequestMapping("/loanList.json")
	@ResponseBody
	public JqGridPage loanList(ItbProjectInfo itbProjectInfo, HttpServletRequest request) {
		logger.info("查询待放款标的信息...");
		itbProjectInfo.setStatus(ProjectStatusEnum.CHARGED.getCode());
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbProjectInfo> pages = itbProjectInfoService.findPage(itbProjectInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	@RequestMapping("/projectList.html")
	public  String  projectList(){
		return "/loanManageMent/projectList";
	}
	/**
	 * 待满标的信息列表页
	 * 
	 * @return
	 */
	@RequestMapping("/projectList.json")
	@ResponseBody
	public JqGridPage projectList(VItbProjectAll itbProjectInfo, HttpServletRequest request) {
		logger.info("查询待满标的信息...");
		itbProjectInfo.setStatus(ProjectStatusEnum.INVESTMENT.getCode());
		Pageable pageable = PageUtils.getPageable(request);
		Page<VItbProjectAll> pages = vItbProjectAllService.findPage(itbProjectInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	/**
	 * 满标操作
	 * @param id 标的id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fullProject")
	public JsonResult fullProject(Long id,HttpServletRequest request ){
		try {
			return itbProjectInfoService.fullProject(id,IpHelper.getIpAddress(request));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("满标操作异常:",e);
			return jsonResultHelper.buildFailJsonResult(ExceptionResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 放款
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pay")
	public JsonResult pay(Long id,HttpServletRequest request){
		try {
			return itbProjectInfoService.pay(id,IpHelper.getIpAddress(request));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("满标操作异常:",e);
			return jsonResultHelper.buildFailJsonResult(ExceptionResultCode.SYSTEM_ERROR);
		}
	}
	
	@RequestMapping("/chargeList.html")
	public  String  chargeList(){
		return "/loanManageMent/chargeList";
	}
	/**
	 * 待平台收费标的信息列表页
	 * 
	 * @return
	 */
	@RequestMapping("/chargeList.json")
	@ResponseBody
	public JqGridPage chargeList(VItbProjectAll vItbProjectAll, HttpServletRequest request) {
		logger.info("查询待平台收费标的信息...");
		vItbProjectAll.setStatus(ProjectStatusEnum.FULL.getCode());
		Pageable pageable = PageUtils.getPageable(request);
		Page<VItbProjectAll> pages = vItbProjectAllService.findPage(vItbProjectAll, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 平台收费
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/charge")
	public JsonResult charge(Long id,HttpServletRequest request){
		try {
			return itbProjectInfoService.charge(id,IpHelper.getIpAddress(request));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("平台收费操作异常:",e);
			return jsonResultHelper.buildFailJsonResult(ExceptionResultCode.SYSTEM_ERROR);
		}
	}
	
	@RequestMapping("/advanceList.html")
	public  String  advanceList(){
		return "/loanManageMent/advanceList";
	}
	/**
	 * 待偿付标的信息列表页
	 * 
	 * @return
	 */
	@RequestMapping("/advanceList.json")
	@ResponseBody
	public JqGridPage advanceList(ItbProjectInfo itbProjectInfo, HttpServletRequest request) {
		logger.info("查询待偿付标的信息...");
		itbProjectInfo.setStatus(ProjectStatusEnum.ADVANCELIST.getCode());
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbProjectInfo> pages = itbProjectInfoService.findPage(itbProjectInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 偿付
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/advance")
	public JsonResult advance(Long id,HttpServletRequest request){
		try {
			return itbProjectInfoService.advance(id,IpHelper.getIpAddress(request));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("偿付操作异常:",e);
			return jsonResultHelper.buildFailJsonResult(ExceptionResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * 贴现
	 * @return
	 */
	@RequestMapping("/discountList.html")
	public  String  discountList(){
		return "/loanManageMent/discountList";
	}
	/**
	 * 待贴现标的信息列表页
	 * 
	 * @return
	 */
	@RequestMapping("/discountList.json")
	@ResponseBody
	public JqGridPage discountList(ItbProjectInfo itbProjectInfo, HttpServletRequest request) {
		logger.info("查询待贴现标的信息...");
		itbProjectInfo.setStatus(ProjectStatusEnum.DISCOUNTLIST.getCode());
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbProjectInfo> pages = itbProjectInfoService.findPage(itbProjectInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 贴现
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/discount")
	public JsonResult discount(Long id,HttpServletRequest request){
		try {
			return itbProjectInfoService.discount(id,IpHelper.getIpAddress(request));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贴现操作异常:",e);
			return jsonResultHelper.buildFailJsonResult(ExceptionResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * 标的信息查询
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryProject.json")
	@ResponseBody
	public Map<String, Object> queryProject(Long projectId) throws Exception{
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.findById(projectId);
		ProjectRequest projectRequest = new ProjectRequest();
		projectRequest.setProject_id(itbProjectAccount.getPayProjectId());
		ProjectResult projectResult = queryService.project(projectRequest);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("localBalance", itbProjectAccount.getBalance());
		map.put("payBalance", MathUtil.div(new Double(projectResult.getBalance()), 100, 2));
		return map;
	}
	/**
	 * 锁定标的
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lockProject")
	public JsonResult lockProject(Long id){
		try {
			return itbProjectInfoService.lockProject(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("锁定标的异常:",e);
			return jsonResultHelper.buildFailJsonResult(ExceptionResultCode.SYSTEM_ERROR);
		}
	}
	/**
	 * 查询余额
	 * @param view
	 * @param projectId 标的id
	 * @param companyType CompanyTypeEnum.code
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/queryBalance.html")
	public ModelAndView queryBalance(ModelAndView view,Long projectId,String companyType) throws Exception{
		
		ItbProjectAccount itbProjectAccount = itbProjectAccountService.findById(projectId);
		ProjectRequest projectRequest = new ProjectRequest();
		projectRequest.setProject_id(itbProjectAccount.getPayProjectId());
		ProjectResult projectResult = queryService.project(projectRequest);
		view.addObject("localBalance", itbProjectAccount.getBalance());
		view.addObject("payBalance", MathUtil.div(new Double(projectResult.getBalance()), 100, 2));
		if(StringUtils.isNotEmpty(companyType)){
			List<ItbCompanyInfo> companyInfos = itbCompanyInfoService.findByTypeAndStatus(companyType, CompanyStatusEnum.ENABLE.getCode());
			if(CollectionUtils.isNotEmpty(companyInfos)){
				view.addObject("companyBalance",companyInfos.get(0).getBalance());
			}
			view.addObject("companyName", CompanyTypeEnum.getDescByCode(companyType));
			EnterpriseRequest enterprise = new EnterpriseRequest();
			enterprise.setQuery_mer_id(companyInfos.get(0).getPayId());
			EnterpriseResult enterpriseResult = queryPtpMerService.enterprise(enterprise);
			view.addObject("companyPayBalance",MathUtil.div(new Double(enterpriseResult.getBalance()), 100, 2));
		}
		view.setViewName("loanManageMent/queryBalance");
		return view;
	}
	/**
	 * 初始化标的信息列表
	 * @return
	 */
	@RequestMapping("/transfer/projectInfoList.html")
	public String projectInfoList() {
		return "/transfer/projectInfoList";
	}

	@RequestMapping("/transfer/projectInfoList.json")
	@ResponseBody
	public JqGridPage projectInfoList(ItbProjectInfo itbProjectInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbProjectInfo> pages = itbProjectInfoService.findPage(itbProjectInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
