package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.VItbProjectAll;
import com.itanbank.account.domain.web.VItbUserAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VItbProjectAllRepository extends JpaRepository<VItbProjectAll, Long>, JpaSpecificationExecutor<VItbProjectAll> {

}
