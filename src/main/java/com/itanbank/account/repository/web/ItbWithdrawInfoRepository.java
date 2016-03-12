package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbRechargeInfo;
import com.itanbank.account.domain.web.ItbWithdrawInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by SDD on 2016/2/25.
 */
public interface ItbWithdrawInfoRepository extends JpaRepository<ItbWithdrawInfo,Long>,JpaSpecificationExecutor<ItbWithdrawInfo> {
}
