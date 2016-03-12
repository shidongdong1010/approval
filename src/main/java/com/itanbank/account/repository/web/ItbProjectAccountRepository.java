package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbProjectAccount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItbProjectAccountRepository extends JpaRepository<ItbProjectAccount,Long>,JpaSpecificationExecutor<ItbProjectAccount>{

    @Modifying
    @Query(value = "update itb_project_account t set t.balance = t.balance + ?1, t.version = t.version + 1 where t.id = ?2 and t.version = ?3", nativeQuery = true)
    int updateBalanceByIdAndVersion(Double amount, Long id, Long version);

    @Modifying
    @Query(value = "update itb_project_account t set t.can_amount = t.can_amount + ?1, t.version = t.version + 1 where t.id = ?2 and t.version = ?3", nativeQuery = true)
    int updateCanAmountByIdAndVersion(Double amount, Long id, Long version);

    /**
     * 更新标的余额 -- 贴现业务
     * @param amount
     * @param isAddAmount
     * @param id
     * @param version
     * @return
     */
    @Modifying
    @Query(value = "update itb_project_account t set t.balance = t.balance + ?1, t.version = t.version + 1, t.add_amount = ?1, t.is_add_amount = ?2  where t.id = ?3 and t.version = ?4", nativeQuery = true)
    int updateDiscountByIdAndVersion(Double amount, String isAddAmount, Long id, Long version);

    /**
     * 更新标的余额 -- 服务费业务
     * @param amount
     * @param serverAmount
     * @param id
     * @param version
     * @return
     */
    @Modifying
    @Query(value = "update itb_project_account t set t.balance = t.balance + ?1, t.version = t.version + 1, t.grant_server_amount = ?2  where t.id = ?3 and t.version = ?4", nativeQuery = true)
    int updateServerAmountByIdAndVersion(Double amount, Double serverAmount, Long id, Long version);

    @Modifying
    @Query(value = "update itb_project_account t set t.put_amount = t.put_amount + ?1, t.put_num = t.put_num+1,t.balance = t.balance + ?1, t.version = t.version + 1 where t.id = ?2 and t.version = ?3", nativeQuery = true)
    int updateFullProjectByIdAndVersion(Double amount, Long id, Long version);
    
    @Modifying
    @Query(value = "update itb_project_account t set t.is_advance =?2, t.balance = t.balance + ?1, t.version = t.version + 1 where t.id = ?3 and t.version = ?4", nativeQuery = true)
    int updateAdvanceByIdAndVersion(Double amount, String isAdvance,Long id, Long version);
}
