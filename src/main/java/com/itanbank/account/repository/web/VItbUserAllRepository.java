package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.VItbUserAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VItbUserAllRepository extends JpaRepository<VItbUserAll, Long>, JpaSpecificationExecutor<VItbUserAll> {

}
