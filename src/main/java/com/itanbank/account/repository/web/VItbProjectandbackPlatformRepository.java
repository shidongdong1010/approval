package com.itanbank.account.repository.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itanbank.account.domain.web.VItbProjectandbackPlatform;


public interface VItbProjectandbackPlatformRepository extends JpaRepository<VItbProjectandbackPlatform, Long>, JpaSpecificationExecutor<VItbProjectandbackPlatform> {

}
