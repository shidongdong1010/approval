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
import com.itanbank.account.domain.web.ItbWithdrawInfo;
import com.itanbank.account.service.ItbWithdrawInfoService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbWithdrawInfoController {
	@Autowired
	private  ItbWithdrawInfoService  itbWithdrawInfoService;
	
	@RequestMapping("/transfer/withdrawInfoList.html")
	public String withdrawInfoList() {
		return "/transfer/withdrawInfoList";
	}

	@RequestMapping("/transfer/withdrawInfoList.json")
	@ResponseBody
	public JqGridPage withdrawInfoList(ItbWithdrawInfo withdrawInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbWithdrawInfo> pages = itbWithdrawInfoService.findPage(withdrawInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
