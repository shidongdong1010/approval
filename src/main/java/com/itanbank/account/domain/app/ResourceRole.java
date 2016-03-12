package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 角色与资源关系表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "s_resource_role")
public class ResourceRole implements Serializable {

    /**  **/
    private Long id;

    /** 资源ID **/
    private Long resourceId;

    /** 角色ID **/
    private Long roleId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "resource_id", length = 11)
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Column(name = "role_id", length = 11)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
