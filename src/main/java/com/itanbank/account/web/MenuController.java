package com.itanbank.account.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.itanbank.account.common.AuthUtil;
import com.itanbank.account.domain.app.pojo.MenuTree;
import com.itanbank.account.common.JsonResult;
import com.itanbank.account.common.JsonResultHelper;
import com.itanbank.account.common.page.JqGridPage;
import com.itanbank.account.common.page.PageUtils;
import com.itanbank.account.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itanbank.account.domain.app.Menu;
import com.itanbank.account.domain.app.resultcode.UserResultCode;

/**
 * 菜单管理
 * Created by peng_wang on 16/2/17.
 */
@Controller
public class MenuController {
	@Autowired
	private JsonResultHelper jsonResultHelper;
	
	@Autowired
	private MenuService menuService;

	@Value("${itanbank.system.id}")
	private Long systemId;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @RequestMapping("/menus")
    public ModelAndView menus(ModelAndView view) {
		view.setViewName("/menus/menus");
		return view;
	}
    
	/**
	 * 菜单列表页
	 * 
	 * @return
	 */
	@RequestMapping("/menu.json")
	@ResponseBody
	public JqGridPage menuList(Menu menu, HttpServletRequest request) {
		logger.info("查询菜单列表");
		// Pageable 创建，提供页数，是当前显示条数
		Pageable pageable = PageUtils.getPageable(request);
		Page<Menu> pages = menuService.findPage(menu, pageable);
		return PageUtils.toJqGridPage(pages);
	}

	/**
	 * 修改菜单界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/editMenu.html")
	public String editMenu(HttpServletRequest request, Long id){
		Menu menu = menuService.findById(id);
		request.setAttribute("menu", menu);
		return "/menus/editMenu";
	}

	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editMenu")
	public JsonResult editMenu(Menu menu){
		// 修改
		Menu menus = menuService.findById(menu.getId());
		if(null != menus){
			menus.setName(menu.getName());
			menus.setUrl(menu.getUrl());
			menus.setSort(menu.getSort());
			menus.setIcon(menu.getIcon());
			menus.setSysId(menu.getSysId());
			menuService.update(menus);
			//修改成功
			return jsonResultHelper.buildSuccessJsonResult(null);
		}else{
			return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delMenu")
	public JsonResult delMenu(Long id){
		logger.info("删除菜单:{}", id);
		Menu menu = menuService.findById(id);
		if(null != menu){
			 if(null != menu.getParentId()){
				 //说明是子菜单删除
				 menuService.deleteMenu(id);
			 }else{
				 //说明是父菜单删除，及要删除下面的子菜单
				 List<Menu>  menuList = menuService.findAllById(id);
				 menuService.deleteMenuAll(menuList);
				 //在删除自己
				 menuService.deleteMenu(id);
			 }
			 return jsonResultHelper.buildSuccessJsonResult(null);
		}else{
			return jsonResultHelper.buildFailJsonResult(UserResultCode.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 新增菜单界面
	 * @param request
	 * @return
	 */
	@RequestMapping("/addMenu.html")
	public String addMenu(HttpServletRequest request){
		String parentId = request.getParameter("parentId");
		request.setAttribute("parentId",parentId);;
		return "/menus/addMenu";
	}
	
	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addMenu")
	public JsonResult addMenu(Menu menu){
		logger.info("添加菜单..");
		menuService.update(menu);
		return jsonResultHelper.buildSuccessJsonResult(null);
	}

	/**
	 * 左边菜单
	 * @param request
	 * @return
	 */
	@RequestMapping("/sidebar.html")
	public String sidebar(HttpServletRequest request){
		// 加载用户可访问的菜单
		Long userId = AuthUtil.getUserId();
		if(userId != null){
			List<MenuTree> menus = menuService.findByUserId(userId, systemId);
			request.setAttribute("menus", menus);
		}
		return "/menus/sidebar";
	}
}
