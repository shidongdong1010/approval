package com.itanbank.account.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itanbank.account.domain.web.VItbProjectandbackAdvance;

import java.util.List;

public interface VItbProjectandbackAdvanceService {
	
    public VItbProjectandbackAdvance findById(Long id);


    public Page<VItbProjectandbackAdvance> findPage(final VItbProjectandbackAdvance example, Pageable page) ;

    List<VItbProjectandbackAdvance> findAll();
}
