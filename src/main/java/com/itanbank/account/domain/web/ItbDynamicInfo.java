package com.itanbank.account.domain.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 动态新闻
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "itb_dynamic_info")
public class ItbDynamicInfo implements Serializable {

    /** 主键 **/
    private Long id;

    /** 标题 **/
    private String title;

    /** 内容 **/
    private String content;

    /** 发布时间 **/
    private Date pushTime;

    /** 创建时间 **/
    private Date createTime;

    /** 创建用户ID **/
    private Long createUserId;

    /** 状态[0-新建, 1-已发布, 2-下线] **/
    private String status;

    /** 排序 **/
    private Long sort;

    /** 动态类型[0-公司动态, 1-新闻] **/
    private String dynamicType;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title", length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "push_time")
    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "create_user_id", length = 11)
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "status", length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "sort", length = 11)
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Column(name = "dynamic_type", length = 1)
    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

}
