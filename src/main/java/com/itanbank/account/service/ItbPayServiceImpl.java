package com.itanbank.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.web.ItbPayInfo;
import com.itanbank.account.repository.web.ItbPayRepository;

@Service
public class ItbPayServiceImpl implements ItbPayService{

	@Autowired
	private ItbPayRepository itbPayRepository;

	@Override
	public ItbPayInfo save(ItbPayInfo itbPayInfo){
		return itbPayRepository.save(itbPayInfo);
	}
}
