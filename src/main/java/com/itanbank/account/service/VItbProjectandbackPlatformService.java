package com.itanbank.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.VItbProjectandbackPlatform;

public interface VItbProjectandbackPlatformService {
	
	 public VItbProjectandbackPlatform findById(Long id);

	 public Page<VItbProjectandbackPlatform> findPage(final VItbProjectandbackPlatform example, Pageable page) ;
}
