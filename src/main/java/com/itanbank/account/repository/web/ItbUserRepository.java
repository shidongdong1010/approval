package com.itanbank.account.repository.web;

import com.itanbank.account.domain.web.ItbInvestInfo;
import com.itanbank.account.domain.web.ItbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItbUserRepository extends JpaRepository<ItbUser, Long>, JpaSpecificationExecutor<ItbUser> {

}
