package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbUser;
import com.itanbank.account.domain.web.ItbUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItbUserAccountRepository extends JpaRepository<ItbUserAccount, Long>, JpaSpecificationExecutor<ItbUserAccount> {

    @Modifying
    @Query(value = "update itb_user_account t set t.used_amount = t.used_amount + ?1, t.total_amount = t.total_amount + ?1, t.version = t.version + 1 where t.id = ?2 and t.version = ?3", nativeQuery = true)
    int updateBalanceByIdAndVersion(Double amount, Long id, Long version);
}
