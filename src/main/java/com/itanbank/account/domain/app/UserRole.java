package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 用户与角色关系表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "s_user_role")
public class UserRole implements Serializable {

    /** 主键 **/
    private Long id;

    /** 用户ID **/
    private Long userId;

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

    @Column(name = "user_id", length = 11)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "role_id", length = 11)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
