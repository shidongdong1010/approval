package com.itanbank.account.repository.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbProjectInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItbProjectInfoRepository extends JpaRepository<ItbProjectInfo,Long>,JpaSpecificationExecutor<ItbProjectInfo>{

	/**
	 * 根据状态查询
	 * @param status
	 * @return
	 */
	List<ItbProjectInfo> findByStatus(String status);

	Page<ItbProjectInfo> findAll(Specification<ItbProjectInfo> spec, Pageable page);

	/**
	 * 查询未完成的返款记录
	 * @return
	 */
	@Query(value = "SELECT * from itb_project_info t where t.status <> '9' and EXISTS (SELECT 1 from itb_back_advance_info a WHERE t.id = a.project_id and a.status = '0')", nativeQuery = true)
	List<ItbProjectInfo> findForAdvance();
}
