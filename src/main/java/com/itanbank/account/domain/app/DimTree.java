package com.itanbank.account.domain.app;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 字典类型
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "dim_tree")
public class DimTree implements Serializable {

    /** 主键 **/
    private Long id;

    /** 字典类型代码 **/
    private String treeNo;

    /** 字典类型名称 **/
    private String treeName;


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

    @Column(name = "tree_name", length = 45)
    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

}
