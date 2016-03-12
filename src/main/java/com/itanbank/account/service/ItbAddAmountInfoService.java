package com.itanbank.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.ItbAddAmountInfo;

/**
 * 贴现记录
 * @author wn
 *
 */
public interface ItbAddAmountInfoService {

	/**
	 * 保存对象
	 * @param advanceInfo
	 * @return
	 */
	ItbAddAmountInfo save(ItbAddAmountInfo addAmountInfo);

	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbAddAmountInfo> findPage(ItbAddAmountInfo example, Pageable page);
}
