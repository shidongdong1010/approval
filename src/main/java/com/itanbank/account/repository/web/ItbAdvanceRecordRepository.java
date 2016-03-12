package com.itanbank.account.repository.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbAdvanceRecordInfo;

public interface ItbAdvanceRecordRepository extends JpaRepository<ItbAdvanceRecordInfo, Long>,JpaSpecificationExecutor<ItbAdvanceRecordInfo>{

	List<ItbAdvanceRecordInfo> findByProjectId(Long projectId);
	
	List<ItbAdvanceRecordInfo> findByAdvanceId(Long advanceId);
}
