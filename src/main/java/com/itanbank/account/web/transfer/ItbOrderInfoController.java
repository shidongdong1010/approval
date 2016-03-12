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
import com.itanbank.account.domain.web.ItbOrderInfo;
import com.itanbank.account.service.ItbOrderInfoService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbOrderInfoController {
	@Autowired
	private  ItbOrderInfoService  itbOrderInfoService;
	
	/**
	 * 订单记录
	 * @return
	 */
	@RequestMapping("/transfer/orderInfoList.html")
	public String orderInfoList() { 
		return "/transfer/orderInfoList";
	}

	@RequestMapping("/transfer/orderInfoList.json")
	@ResponseBody
	public JqGridPage orderInfoList(ItbOrderInfo itbOrderInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbOrderInfo> pages = itbOrderInfoService.findPage(itbOrderInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
