package com.itanbank.account.web.transfer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.domain.web.ItbBackPlatformInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.service.ItbBackAdvanceInfoService;
import com.itanbank.account.service.ItbBackPlatformInfoService;
import com.itanbank.account.service.ItbBackUserInfoService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbBackQueryInfoController {
	
	@Autowired
	private ItbBackUserInfoService  itbBackUserInfoService;
	
	@Autowired 
	private ItbBackAdvanceInfoService itbBackAdvanceInfoService;
	
	@Autowired
	private ItbBackPlatformInfoService  itbBackPlatformInfoService;
	
	/**
	 * 返款到个人
	 * @return
	 */
	@RequestMapping("/transfer/backToUserList.html")
	public String backToUserList() { 
		return "/transfer/backToUserList";
	}

	@RequestMapping("/transfer/backToUserList.json")
	@ResponseBody
	public JqGridPage backToUserList(ItbBackUserInfo itbBackUserInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbBackUserInfo> pages = itbBackUserInfoService.findPage(itbBackUserInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 返款到垫付方
	 * @return
	 */
	@RequestMapping("/transfer/backToAdvanceList.html")
	public String backToAdvanceList() {
		return "/transfer/backToAdvanceList";
	}

	@RequestMapping("/transfer/backToAdvanceList.json")
	@ResponseBody
	public JqGridPage backToAdvanceList(ItbBackAdvanceInfo itbBackAdvanceInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbBackAdvanceInfo> pages = itbBackAdvanceInfoService.findPage(itbBackAdvanceInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 返款到平台
	 * @return
	 */
	@RequestMapping("/transfer/backToPlatformList.html")
	public String backToPlatformList() {
		return "/transfer/backToPlatformList";
	}

	@RequestMapping("/transfer/backToPlatformList.json")
	@ResponseBody
	public JqGridPage backToPlatformList(ItbBackPlatformInfo itbBackPlatformInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbBackPlatformInfo> pages = itbBackPlatformInfoService.findPage(itbBackPlatformInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
