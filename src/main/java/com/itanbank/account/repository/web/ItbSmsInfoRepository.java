package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itanbank.account.domain.web.ItbSmsInfo;

public interface ItbSmsInfoRepository extends JpaRepository<ItbSmsInfo, Long>{
	
}
