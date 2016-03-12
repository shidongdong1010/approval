package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 资源表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "s_resource")
public class Resource implements Serializable {

    /** 主键 **/
    private Long id;

    /** 资源名称 **/
    private String resourceName;

    /** 资源类型 **/
    private String resourceType;

    /** 资源URL **/
    private String resourceContent;

    /** 资源描述 **/
    private String resourceDesc;

    /** 状态(0-正常,1-失效) **/
    private Long enabled;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "resource_name", length = 100)
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Column(name = "resource_type", length = 100)
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Column(name = "resource_content", length = 200)
    public String getResourceContent() {
        return resourceContent;
    }

    public void setResourceContent(String resourceContent) {
        this.resourceContent = resourceContent;
    }

    @Column(name = "resource_desc", length = 200)
    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    @Column(name = "enabled", length = 2)
    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

}
