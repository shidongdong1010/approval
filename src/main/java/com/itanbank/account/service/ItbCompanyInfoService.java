package com.itanbank.account.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.ItbCompanyInfo;

public interface ItbCompanyInfoService {

	ItbCompanyInfo findById(Long id);

	ItbCompanyInfo findByPayId(String payId);

	/**
	 * 分页查询企业信息
	 * @param example
	 * @param page
	 * @return
	 */
	public Page<ItbCompanyInfo> findPage(ItbCompanyInfo example, Pageable page);
	/**
     * 根据类型查询
     * @param type
     * @return
     */
	List<ItbCompanyInfo> findByTypeAndStatus(String type,String status);

	/***
	 * 更新企业账户信息
	 * @param amount
	 * @param id
	 * @param version
	 * @return
	 */
	boolean updateBalanceByIdAndVersion(Double amount, Long id, Long version);
	
	/**
	 * 查询企业账户余额
	 * @param id
	 * @return
	 */
	public Double  queryCompanyAmount(Long  id)throws Exception;

	/**
	 * 保存对象
	 * @param companyInfo
	 * @return
	 */
	ItbCompanyInfo saveItbCompanyInfo(ItbCompanyInfo companyInfo);
}
