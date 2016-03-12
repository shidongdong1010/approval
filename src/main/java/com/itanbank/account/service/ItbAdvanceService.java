package com.itanbank.account.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.ItbAdvanceInfo;

/**
 * 偿付
 * @author wn
 *
 */
public interface ItbAdvanceService {

	/**
	 * 保存对象
	 * @param advanceInfo
	 * @return
	 */
	ItbAdvanceInfo save(ItbAdvanceInfo advanceInfo);

	/**
	 * 计算代偿金额
	 * 代偿条件：当日到期且未还清的还款信息；
	 * 代偿金额：当期应还金额减指定时间前已还金额
	 * @throws Exception
	 */
	void calculateAdvanceAmount() throws Exception;

	/**
	 * 根据垫付时间查询垫付信息
	 * @param advanceTime
	 * @return
	 */
	List<ItbAdvanceInfo> findByAdvanceTime(String advanceTime);

	/**
	 * 删除
	 * @param entity
	 */
	void delete(ItbAdvanceInfo entity);
	
	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbAdvanceInfo> findPage(ItbAdvanceInfo example, Pageable page);

}
