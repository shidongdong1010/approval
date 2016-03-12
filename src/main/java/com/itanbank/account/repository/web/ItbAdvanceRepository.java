package com.itanbank.account.repository.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itanbank.account.domain.web.ItbAdvanceInfo;

public interface ItbAdvanceRepository extends JpaRepository<ItbAdvanceInfo, Long>,JpaSpecificationExecutor<ItbAdvanceInfo>{

	@Query(nativeQuery = true,value="select * from itb_advance_info t where DATE_FORMAT(t.advance_time,'%Y-%m-%d') =?1")
	public List<ItbAdvanceInfo> findByAdvanceTime(String advanceTime);
}
