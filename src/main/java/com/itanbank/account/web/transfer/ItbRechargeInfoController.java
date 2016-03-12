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
import com.itanbank.account.domain.web.ItbRechargeInfo;
import com.itanbank.account.service.ItbRechargeInfoService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbRechargeInfoController {
	
	@Autowired
	private  ItbRechargeInfoService  itbRechargeInfoService;
	
	@RequestMapping("/transfer/rechargeInfoList.html")
	public String rechargeInfoList() {
		return "/transfer/rechargeInfoList";
	}

	@RequestMapping("/transfer/rechargeInfoList.json")
	@ResponseBody
	public JqGridPage rechargeInfoList(ItbRechargeInfo rechargeInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbRechargeInfo> pages = itbRechargeInfoService.findPage(rechargeInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
