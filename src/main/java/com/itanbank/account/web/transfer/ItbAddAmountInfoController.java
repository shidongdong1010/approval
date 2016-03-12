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
import com.itanbank.account.domain.web.ItbAddAmountInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.service.ItbAddAmountInfoService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbAddAmountInfoController {
	@Autowired
	private ItbAddAmountInfoService ItbAddAmountInfoService;
	
	/**
	 * 贴现记录
	 * @return
	 */
	@RequestMapping("/transfer/addAmountInfoList.html")
	public String addAmountInfoList() { 
		return "/transfer/addAmountInfoList";
	}

	@RequestMapping("/transfer/addAmountInfoList.json")
	@ResponseBody
	public JqGridPage addAmountInfoList(ItbAddAmountInfo itbAddAmountInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbAddAmountInfo> pages = ItbAddAmountInfoService.findPage(itbAddAmountInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
