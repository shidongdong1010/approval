package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbRechargeInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by SDD on 2016/2/25.
 */
public interface ItbRechargeInfoRepository extends JpaRepository<ItbRechargeInfo,Long>,JpaSpecificationExecutor<ItbRechargeInfo> {
	List<ItbRechargeInfo>  findByStatus(String  status);
}
