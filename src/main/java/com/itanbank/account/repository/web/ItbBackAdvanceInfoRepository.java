package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.domain.web.ItbInvestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItbBackAdvanceInfoRepository extends JpaRepository<ItbBackAdvanceInfo,Long>,JpaSpecificationExecutor<ItbBackAdvanceInfo> {

    ItbBackAdvanceInfo findByProjectIdAndCompanyId(Long projectId, Long companyId);
}
