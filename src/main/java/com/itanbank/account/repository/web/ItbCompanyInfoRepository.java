package com.itanbank.account.repository.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbCompanyInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ItbCompanyInfoRepository extends JpaRepository<ItbCompanyInfo, Long>, JpaSpecificationExecutor<ItbCompanyInfo> {

    Page<ItbCompanyInfo> findAll(Specification<ItbCompanyInfo> spec, Pageable page);

    public ItbCompanyInfo findByPayId(String payId);

    @Modifying
    @Query(value = "update itb_company_info t set t.balance = t.balance + ?1, t.version = t.version + 1 where t.id = ?2 and t.version = ?3", nativeQuery = true)
    int updateBalanceByIdAndVersion(Double amount, Long id, Long version);

    /**
     * 根据类型查询企业用户
     *
     * @param type
     * @return
     */
    List<ItbCompanyInfo> findByTypeAndStatus(String type, String status);


}
