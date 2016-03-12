package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbInvestInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItbInvestRepository extends JpaRepository<ItbInvestInfo,Long>,JpaSpecificationExecutor<ItbInvestInfo> {

    List<ItbInvestInfo> findByProjectId(Long projectId);

    List<ItbInvestInfo> findByProjectIdAndUserId(Long projectId, Long userId);

    /**
     * 统计标的的投资记录
     * @param projectId
     * @param status
     * @return
     */
    Long countByProjectIdAndStatus(Long projectId, String status);

    /**
     * 查询标的的投资记录
     * @param projectId
     * @param status
     * @return
     */
    List<ItbInvestInfo> findByProjectIdAndStatus(Long projectId, String status);

    /**
     * 统计标中已经返款的投资记录数
     * @param projectId
     * @return
     */
    @Query(value = "SELECT count(1) FROM itb_invest_info a WHERE a.project_id= ?1 and a.status = 0 and EXISTS (" +
            " SELECT 1 FROM itb_back_user_info b WHERE b.status = 0 and a.project_id = b.project_id and a.user_id = b.user_id" +
            " and a.invest_user_type = b.user_type)", nativeQuery = true)
    Long countByBackUser(Long projectId);
    
    /**
     * 查询标的应发收益
     * @param projectId
     * @return
     */
    @Query(nativeQuery=true,value="select sum(IFNULL(t.return_amount,0)) from itb_invest_info t where t.project_id=?1")
    Double queryProjectSumInterest(Long projectId);
}
