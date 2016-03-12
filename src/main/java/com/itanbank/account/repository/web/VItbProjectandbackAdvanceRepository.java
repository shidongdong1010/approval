package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.VItbProjectandbackAdvance;


public interface VItbProjectandbackAdvanceRepository extends JpaRepository<VItbProjectandbackAdvance, Long>, JpaSpecificationExecutor<VItbProjectandbackAdvance> {

}
