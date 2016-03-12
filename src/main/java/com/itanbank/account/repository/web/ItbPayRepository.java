package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbPayInfo;

public interface ItbPayRepository extends JpaRepository<ItbPayInfo,Long>,JpaSpecificationExecutor<ItbPayInfo> {

}
