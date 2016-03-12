package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbBackPlatformInfo;
import com.itanbank.account.domain.web.ItbBackUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItbBackPlatformInfoRepository extends JpaRepository<ItbBackPlatformInfo,Long>,JpaSpecificationExecutor<ItbBackPlatformInfo> {

    /**
     * 查询标的的返款记录
     * @param projectId
     * @return
     */
    ItbBackPlatformInfo findByProjectId(Long projectId);

    ItbBackPlatformInfo findByProjectIdAndCompanyId(Long projectId, Long companyId);
}
