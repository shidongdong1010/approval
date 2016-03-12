package com.itanbank.account.domain.app.pojo;

import com.itanbank.account.domain.app.Menu;

import java.util.List;

/**
 * Created by dongdongshi on 16/2/17.
 */
public class MenuTree {
    /** 主键 **/
    private Long id;

    /** 菜单名称 **/
    private String name;

    /** 菜单URL **/
    private String url;

    /** 父菜单 **/
    private Long parentId;

    /** 排序 **/
    private Long sort;

    /** 系统ID **/
    private Long sysId;

    /**显示图标**/
    private String icon;

    /** 子菜单 **/
    private List<MenuTree> childrens = null;

    public MenuTree(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.parentId = menu.getParentId();
        this.sort = menu.getSort();
        this.sysId = menu.getSysId();
        this.icon = menu.getIcon();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuTree> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuTree> childrens) {
        this.childrens = childrens;
    }
}
