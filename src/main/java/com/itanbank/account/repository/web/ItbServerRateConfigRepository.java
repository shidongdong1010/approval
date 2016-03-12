package com.itanbank.account.repository.web;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itanbank.account.domain.web.ItbServerRateConfig;

public interface ItbServerRateConfigRepository extends JpaRepository<ItbServerRateConfig,Long>,JpaSpecificationExecutor<ItbServerRateConfig>{
	
	@Query("select  u  from ItbServerRateConfig  u where (u.beginTime<=sysdate() and u.endTime>=sysdate())  and  u.status='0'")
	ItbServerRateConfig  queryConfigInfo(); 
}
