package com.itanbank.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.ItbBackAdvanceInfo;
import com.itanbank.account.domain.web.ItbBackPlatformInfo;

/**
 * 返款到平台记录
 * @author wn
 *
 */
public interface ItbBackPlatformInfoService {
		/**
		 * 分页查询
		 * @param example
		 * @param page
		 * @return
		 */
		Page<ItbBackPlatformInfo> findPage(ItbBackPlatformInfo example, Pageable page);
		
		/**
		 * 根据标的ID和垫付公司查询
		 * @param projectId
		 * @param companyId
		 * @return
		 */
		ItbBackPlatformInfo findByProjectIdAndCompanyId(Long projectId, Long companyId);
}
