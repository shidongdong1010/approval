package com.itanbank.account.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.common.JsonResult;
import com.itanbank.account.domain.web.ItbAdvanceRecordInfo;
import com.itanbank.account.pay.model.project.ProjectTransferResult;

/**
 * 偿付记录
 * @author wn
 *
 */
public interface ItbAdvanceRecordService {

	/**
	 * 保存对象
	 * @param advanceInfo
	 * @return
	 */
	ItbAdvanceRecordInfo save(ItbAdvanceRecordInfo advanceRecordInfo);

	/**
	 * 保存对象集合
	 * @param advanceRecordInfos
	 * @return
	 */
	List<ItbAdvanceRecordInfo> saveAll(List<ItbAdvanceRecordInfo> advanceRecordInfos);

	/**
	 * 分页查询
	 * @param example
	 * @param page
	 * @return
	 */
	Page<ItbAdvanceRecordInfo> findPage(ItbAdvanceRecordInfo example, Pageable page);

	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	ItbAdvanceRecordInfo findOne(Long id);


	/**
	 * 根据标的查询代偿记录
	 * @param projectId
	 * @return
	 */
	List<ItbAdvanceRecordInfo> findByProjectId(Long projectId);

	/**
	 * 根据垫付id查询垫付记录
	 * @param advanceId
	 * @return
	 */
	List<ItbAdvanceRecordInfo> findByAdvanceId(Long advanceId);

	/**
	 * 批量删除
	 * @param entities
	 */
	void deleteInBatch(List<ItbAdvanceRecordInfo> entities);

}
