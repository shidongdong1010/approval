package com.itanbank.account.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.common.JsonResultHelper;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.domain.app.Role;
import com.itanbank.account.domain.app.resultcode.UserResultCode;
import com.itanbank.account.domain.web.enums.BusinessTypeEnum;
import com.itanbank.account.pay.enums.ExecuetResultCode;
import com.itanbank.account.pay.enums.ServTypeEnum;
import com.itanbank.account.pay.model.check.DownloadFileRequest;
import com.itanbank.account.pay.model.query.UserRequest;
import com.itanbank.account.pay.model.query.UserResult;
import com.itanbank.account.pay.service.DownAccountService;
import com.itanbank.account.repository.app.OrderNoRepository;
import com.itanbank.account.service.OrderNoService;
import com.itanbank.account.service.RoleService;
import com.itanbank.account.pay.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itanbank.account.domain.app.User;
import com.itanbank.account.service.UserService;

/**
 * Created by dongdongshi on 16/1/13.
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private JsonResultHelper jsonResultHelper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 用户列表页
	 * 
	 * @return
	 */
	@RequestMapping("/userList")
	public ModelAndView userList(ModelAndView view) {
		view.setViewName("/users/userList");
		return view;
	}

	/**
	 * 用户列表页
	 * 
	 * @return
	 */
	@RequestMapping("/userList.json")
	@ResponseBody
	public JqGridPage userList(User user, HttpServletRequest request) {
		logger.info("查询用户列表");
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<User> pages = userService.findPage(user, pageable);
		return PageUtils.toJqGridPage(pages);
	}

	/**
	 * 添加用户界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/addUser.html")
	public String addUser(HttpServletRequest request){
		List<Role> roles = roleService.findAll();
		request.setAttribute("roles", roles);
		return "/users/addUser";
	}

	/**
	 * 添加用户
	 * @param user
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public JsonResult addUser(User user, Long[] roleId){
		logger.info("添加用户:{}, 对应的角色:{}", user, roleId);
		// 检查用户是否存在
		if(userService.findUserByUsername(user.getUsername()) != null){
			return jsonResultHelper.buildFailJsonResult(UserResultCode.LOGIN_USER_NAME_ESISTS);
		}

		// 添加用户
		userService.addUser(user, roleId);
		return jsonResultHelper.buildSuccessJsonResult(null);
	}

	/**
	 * 修改用户界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/editUser.html")
	public String editUser(HttpServletRequest request, Long id){
		User user = userService.findUserById(id);
		request.setAttribute("user", user);

		List<Role> userroles = roleService.findByUserId(id);
		request.setAttribute("userroles", userroles);

		List<Role> roles = roleService.findAll();
		request.setAttribute("roles", roles);
		return "/users/editUser";
	}

	/**
	 * 修改用户
	 * @param user
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editUser")
	public JsonResult editUser(User user, Long[] roleId){
		logger.info("编辑用户:{}, 对应的角色:{}", user, roleId);

		// 修改
		User users = userService.findUserById(user.getId());
		if(null != users){
			users.setFullname(user.getFullname());
			users.setMobile(user.getMobile());
			users.setLocked(user.getLocked());
			users.setEnabled(user.getEnabled());
			users.setExpired(user.getExpired());
			userService.editUser(users, roleId);
			//修改成功
			return jsonResultHelper.buildSuccessJsonResult(null);
		}else{
			return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delUser")
	public JsonResult delUser(Long id){
		logger.info("删除用户:{}", id);
		userService.deleteUser(id);
		return jsonResultHelper.buildSuccessJsonResult(null);
	}

	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetPassword")
	public JsonResult resetPassword(Long id, String password){
		logger.info("重置密码:{}, 密码:{}", id, password);
		// 修改
		User user = userService.findUserById(id);
		// 设置密码
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
		user.setPassword(bc.encode(password));
		user.setLoginErrorCount(0);
		userService.update(user);
		return jsonResultHelper.buildSuccessJsonResult(null);
	}

	// TODO 下面为测试代码，后面将删除
	@Autowired
	private QueryService queryService;
	@Autowired
	private DownAccountService downAccountService;
	@Autowired
	private OrderNoService orderNoService;

	@RequestMapping("/queryPayUser")
	public void queryPayUser() throws Exception {
		UserRequest user = new UserRequest();
		user.setUser_id("UB201601271506540000000000042647");
		user.setIs_find_account("01");
		UserResult userResult = queryService.queryUser(user);
		ExecuetResultCode resultCode = ExecuetResultCode.getByCode(userResult.getRet_code());
		if(ExecuetResultCode.SCUESS.equals(resultCode)){

		}
	}

	@RequestMapping("/downCheckAccountFile")
	public void downCheckAccountFile() throws Exception {
		DownloadFileRequest request = new DownloadFileRequest();
		request.setSettle_date_p2p("20160104");
		request.setSettle_type_p2p("01");
		String url = downAccountService.getDownloadCheckAccountFileUrl(request);
		logger.info(url);
	}

	@RequestMapping("/getOrderNo")
	public void getOrderNo(){
		String orderNo = orderNoService.getOrderNo(BusinessTypeEnum.RECHARGE.getCode());
		logger.info("订单号：" + orderNo);
	}
}
