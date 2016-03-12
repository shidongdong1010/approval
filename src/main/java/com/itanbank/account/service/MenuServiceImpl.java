package com.itanbank.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.app.Menu;
import com.itanbank.account.domain.app.pojo.MenuTree;
import com.itanbank.account.repository.app.MenuRepository;

/**
 * Created by dongdongshi on 16/2/1.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    private final String CACHE_NAME = "userMenuList";
    /**
     * 查询用户拥有的所有菜单
     * @param userId 用户ID
     * @return
     */
    @Cacheable(value = CACHE_NAME)
    @Override
    public List<MenuTree> findByUserId(Long userId, Long sysId){
        List<Menu> menus = menuRepository.findByUserIdAndSysId(userId, sysId);

        // 将菜单转换成MenuTree
        List<MenuTree> trees = new ArrayList<MenuTree>();
        for (Menu menu : menus){
            trees.add(new MenuTree(menu));
        }

        // 构建MenuTree
        List<MenuTree> rootTrees = buildMenuTree(trees);

        return rootTrees;
    }

    /**
     * 构建MenuTree, 使菜单之间都有父子关系
     * @param trees
     * @return
     */
    private List<MenuTree> buildMenuTree(List<MenuTree> trees){
        List<MenuTree> rootTrees = new ArrayList<MenuTree>();
        for (MenuTree tree : trees) {
            if(tree.getParentId() == null || tree.getParentId() == 0){
                rootTrees.add(tree);
            }
            for (MenuTree t : trees) {
                if(t.getParentId() == tree.getId()){
                    if(tree.getChildrens() == null){
                        List<MenuTree> myChildrens = new ArrayList<MenuTree>();
                        myChildrens.add(t);
                        tree.setChildrens(myChildrens);
                    }else{
                        tree.getChildrens().add(t);
                    }
                }
            }
        }
        return rootTrees;
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> findAll(){
        return menuRepository.findAll();
    }

	@Override
	public Menu findById(Long id) {
		return menuRepository.findOne(id);
	}

    @CacheEvict(value = CACHE_NAME, allEntries = true)
	@Override
	public void update(Menu menu) {
		menuRepository.save(menu);
	}

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    @Override
	public void deleteMenu(Long id) {
		menuRepository.delete(id);
	}

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    @Override
	public void deleteMenuAll(List<Menu> menuList) {
		menuRepository.delete(menuList);
	}
	
	@Override
	public List<Menu> findAllById(Long id) {
		return menuRepository.findMenuById(id);
	}
	
	@Override
	public Page<Menu> findPage(final Menu example, Pageable page) {
		 return menuRepository.findAll(new Specification<Menu>() {
	            @Override
	            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
 	                List<Predicate> params = new ArrayList<Predicate>();
	                Path<String> parentIdPath = root.get("parentId");
	                if(null != example.getParentId()){ 
	                    params.add(criteriaBuilder.equal(parentIdPath,example.getParentId()));
	                }else{
	                	params.add(criteriaBuilder.isNull(parentIdPath));
	                }
	                Predicate[] predicates = new Predicate[params.size()];
	                criteriaQuery.where(params.toArray(predicates));
	                return null;
	            }
	        }, page);
	}
}
