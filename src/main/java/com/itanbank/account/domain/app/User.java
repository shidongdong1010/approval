package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "s_user")
public class User implements Serializable {

    /** 主键 **/
    private Long id;

    /** 用户名 **/
    private String username;

    /** 密码 **/
    private String password;

    /** 用户姓名 **/
    private String fullname;

    /** 是否禁用(0-否,1-是) **/
    private Integer enabled = 0;

    /** 密码是否过期(0-正常,1-已过期) **/
    private Integer expired = 0;

    /** 是否锁定(0-正常,1-锁定) **/
    private Integer locked = 0;

    /** 手机号 **/
    private String mobile;

    /** 最后登陆时间 **/
    private Date lastLoginTime;

    /** 登录错误次数 **/
    private Integer loginErrorCount = 0;

    /** 创建时间 **/
    private Date createTime = new Date();

    /** 用户拥有的角色 **/
    private Set<Role> roles = new HashSet<Role>(0);// Code12


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", length = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "fullname", length = 45)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Column(name = "enabled", length = 1)
    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    @Column(name = "expired", length = 1)
    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    @Column(name = "locked", length = 1)
    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Column(name = "mobile", length = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "login_error_count", length = 6)
    public Integer getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(Integer loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Transient
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", enabled=" + enabled +
                ", expired=" + expired +
                ", locked=" + locked +
                ", mobile='" + mobile + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", loginErrorCount=" + loginErrorCount +
                ", createTime=" + createTime +
                ", roles=" + roles +
                '}';
    }
}
