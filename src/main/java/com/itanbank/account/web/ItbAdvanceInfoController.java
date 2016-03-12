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

import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.web.ItbAdvanceInfo;
import com.itanbank.account.domain.web.ItbAdvanceRecordInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.service.ItbAdvanceRecordService;
import com.itanbank.account.service.ItbAdvanceService;

/**
 * 偿付操作
 * @author wn
 *
 */
@Controller
public class ItbAdvanceInfoController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItbAdvanceService itbAdvanceService;
	
	@Autowired
	private ItbAdvanceRecordService itbAdvanceRecordService;
	
	/**
	 * 日垫付
	 * @return
	 */
	@RequestMapping("/transfer/advanceInfoList.html")
	public String advanceInfoList() {
		return "/transfer/advanceInfoList";
	}

	@RequestMapping("/transfer/advanceInfoList.json")
	@ResponseBody
	public JqGridPage advanceInfoList(ItbAdvanceInfo itbAdvanceInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbAdvanceInfo> pages = itbAdvanceService.findPage(itbAdvanceInfo,pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 垫付记录
	 * @return
	 */
	@RequestMapping("/transfer/advanceRecordInfoList.html")
	public String advanceRecordInfoList() {
		return "/transfer/advanceRecordInfoList";
	}

	@RequestMapping("/transfer/advanceRecordInfoList.json")
	@ResponseBody
	public JqGridPage advanceRecordInfoList(ItbAdvanceRecordInfo itbAdvanceRecordInfo, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbAdvanceRecordInfo> pages = itbAdvanceRecordService.findPage(itbAdvanceRecordInfo, pageable);
		return PageUtils.toJqGridPage(pages);
	}
}
