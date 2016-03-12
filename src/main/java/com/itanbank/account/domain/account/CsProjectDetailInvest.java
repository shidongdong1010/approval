package com.itanbank.account.domain.account;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 *
 * @author SDD
 * @version $v: 1.0.0, $time:2015-09-29 15:57:00 Exp $
 */
@Entity
@Table(name = "cs_project_detail_invest")
public class CsProjectDetailInvest implements Serializable {

    /** 主键 **/
    private Long id;

    /**  **/
    private Long projectDetailId;

    /** 投资人 **/
    private String investPerson;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "project_detail_id", length = 11)
    public Long getProjectDetailId() {
        return projectDetailId;
    }

    public void setProjectDetailId(Long projectDetailId) {
        this.projectDetailId = projectDetailId;
    }

    @Column(name = "invest_person", length = 50)
    public String getInvestPerson() {
        return investPerson;
    }

    public void setInvestPerson(String investPerson) {
        this.investPerson = investPerson;
    }

}
