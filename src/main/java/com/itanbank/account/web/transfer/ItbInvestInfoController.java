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
import com.itanbank.account.domain.web.ItbInvestInfo;
import com.itanbank.account.service.ItbInvestService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbInvestInfoController {
	
	@Autowired
	private ItbInvestService  itbInvestService;
	
	@RequestMapping("/transfer/investInfoList.html")
	public String investInfoList(HttpServletRequest request) {
		request.setAttribute("reqProjectId",request.getParameter("reqProjectId"));
		return "/transfer/investInfoList";
	}

	@RequestMapping("/transfer/investInfoList.json")
	@ResponseBody
	public JqGridPage investInfoList(ItbInvestInfo investInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbInvestInfo> pages = itbInvestService.findPage(investInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
