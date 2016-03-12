package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 菜单表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "s_menu")
public class Menu implements Serializable {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "url", length = 60)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "parent_id", length = 11)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "sort", length = 11)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Column(name = "sys_id", length = 11)
    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }
    
    @Column(name = "icon", length = 30)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

    
}
