package com.itanbank.account.service;

import com.itanbank.account.domain.web.ItbBackUserInfo;
import com.itanbank.account.domain.web.ItbInvestInfo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 返款到投资人记录
 * @author wn
 *
 */
public interface ItbBackUserInfoService {

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
	* 分页查询
	* @param example
	* @param page
	* @return
	*/
    Page<ItbBackUserInfo> findPage(ItbBackUserInfo example, Pageable page);
}
