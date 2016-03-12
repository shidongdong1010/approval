package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.domain.web.ItbInvestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItbBackUserInfoRepository extends JpaRepository<ItbBackUserInfo,Long>,JpaSpecificationExecutor<ItbBackUserInfo> {

    /**
     * 统计标的的返款记录
     * @param projectId
     * @param status
     * @return
     */
    Long countByProjectIdAndStatus(Long projectId, String status);

    /**
     * 查询标的的返款记录
     * @param projectId
     * @param status
     * @return
     */
    List<ItbBackUserInfo> findByProjectIdAndStatus(Long projectId, String status);

    /**
     * 查询标的的返款记录
     * @param projectId
     * @return
     */
    List<ItbBackUserInfo> findByProjectId(Long projectId);

    /**
     * 根据投资ID查询
     * @param investId
     * @return
     */
    ItbBackUserInfo findByInvestId(Long investId);
}
