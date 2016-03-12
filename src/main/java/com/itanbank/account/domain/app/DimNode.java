package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * 字典表
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "dim_node")
public class DimNode implements Serializable {

    /** 主键 **/
    private Long id;

    /** 字典类型 **/
    private String treeNo;

    /** 字典项代码 **/
    private String nodeNo;

    /** 字典项名称 **/
    private String nodeName;

    /** 创建时间 **/
    private Date createTime;

    /** 创建人 **/
    private String createName;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tree_no", length = 45)
    public String getTreeNo() {
        return treeNo;
    }

    public void setTreeNo(String treeNo) {
        this.treeNo = treeNo;
    }

    @Column(name = "node_no", length = 45)
    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    @Column(name = "node_name", length = 45)
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "create_name", length = 45)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

}
