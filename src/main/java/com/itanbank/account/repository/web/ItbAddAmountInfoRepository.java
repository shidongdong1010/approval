package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.ItbAddAmountInfo;

public interface ItbAddAmountInfoRepository extends JpaRepository<ItbAddAmountInfo, Long>,JpaSpecificationExecutor<ItbAddAmountInfo>{
}
