package com.itanbank.account.web.transfer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.epbcommons.transformation.math.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itanbank.account.common.CommonUtil;
import com.itanbank.account.common.NumberFormatUtil;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.ItbUserAccount;
import com.itanbank.account.pay.enums.AccountStateEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.model.query.UserRequest;
import com.itanbank.account.pay.model.query.UserResult;
import com.itanbank.account.pay.service.QueryService;
import com.itanbank.account.service.ItbUserAccountService;
import com.itanbank.account.service.ItbUserService;

/**
 * Created by wp on 2016/3/8.
 */
@Controller
public class ItbUserInfoController {
	
	@Autowired
	private  ItbUserService  itbUserService;
	
	@Autowired
	private QueryService queryService;
	
	@Autowired
	private ItbUserAccountService itbUserAccountService;
	
	@RequestMapping("/transfer/userInfoList.html")
	public String userInfoList() {
		return "/transfer/userInfoList";
	}

	@RequestMapping("/transfer/userInfoList.json")
	@ResponseBody
	public JqGridPage userInfoList(ItbUser itbUser, HttpServletRequest request) {
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<ItbUser> pages = itbUserService.findPage(itbUser, pageable);
		return PageUtils.toJqGridPage(pages);
	}
	
	/**
	 * 查询用户详细信息
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/transfer/queryUserInfo.html")
	public String queryUserInfo(HttpServletRequest request,Long id)throws Exception{
		ItbUser   user = new ItbUser();
		ItbUserAccount  userAccount = new ItbUserAccount();
		UserResult userResult = new UserResult();
		if(null != id){
			user = itbUserService.findById(id);
			if(null != user){
				//组装第三方模板
				UserRequest userRequest = new UserRequest();
				userRequest.setUser_id(user.getPayId());
				userAccount = itbUserAccountService.findById(user.getId());
				userResult = queryService.queryUser(userRequest);
		    	if(ExecuetResultCode.SCUESS.getCode().equals(userResult.getRet_code())){
		    		if(!StringUtils.isBlank(userResult.getAccount_state())){
		        		userResult.setAccount_state(AccountStateEnum.getByCode(userResult.getAccount_state()).getDesc());
		        	}
		        	userResult.setIdentity_code(CommonUtil.dealIdCard(userResult.getIdentity_code()));
		        	userResult.setCard_id(CommonUtil.dealBankNo(userResult.getCard_id()));
		        	if(!StringUtils.isBlank(userResult.getBalance())){
		        		userResult.setBalance(NumberFormatUtil.getNumberTwoDecimal(String.valueOf(MathUtil.div(new Double(userResult.getBalance()), 100d, 2)))+"元");
		        	}else{
		        		userResult.setBalance("0元");
		        	}
		    	}
			}
		}
		request.setAttribute("user", user);
		request.setAttribute("userAccount",userAccount);
		request.setAttribute("userResult",userResult);
		return  "/transfer/queryUserInfoContrast";
	}
}
