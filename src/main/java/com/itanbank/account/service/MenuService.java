package com.itanbank.account.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.app.Menu;
import com.itanbank.account.domain.app.pojo.MenuTree;

/**
 * Created by dongdongshi on 16/2/1.
 */
public interface MenuService {

    /**
     * 查询用户拥有的所有菜单
     * @param userId 用户ID
     * @param sysId 系统ID
     * @return
     */
    public List<MenuTree> findByUserId(Long userId, Long sysId);

    /**
     * 查询所有菜单
     * @return
     */
    public List<Menu> findAll();
    
	
	List<Menu> findAllById(Long id);
	Menu  findById(Long  id);
	
	void update(Menu menu);

    void deleteMenu(Long id);
    
    void  deleteMenuAll(List<Menu> menuList);
    /**
     * 分页查询
     * @param example
     * @param page
     * @return
     */
    public Page<Menu> findPage(Menu example, Pageable page);
}
