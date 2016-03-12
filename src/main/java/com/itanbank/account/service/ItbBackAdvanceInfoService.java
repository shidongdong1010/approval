package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbBackAdvanceInfo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 返款到垫付方记录
 * @author wn
 *
 */
public interface ItbBackAdvanceInfoService {

	/**
	 * 根据标的ID和垫付公司查询
	 * @param projectId
	 * @param companyId
	 * @return
	 */
	ItbBackAdvanceInfo findByProjectIdAndCompanyId(Long projectId, Long companyId);
	
	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbBackAdvanceInfo> findPage(ItbBackAdvanceInfo example, Pageable page);
}
