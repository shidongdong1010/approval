package com.itanbank.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsProjectDetailInvest;
import com.itanbank.account.repository.account.CsProjectDetailInvestRepository;

@Service
public class CsProjectDetailInvestServiceImpl implements CsProjectDetailInvestService {
	
	@Autowired
	private CsProjectDetailInvestRepository csProjectDetailInvestRepository;
	
	@Override
	public void saveCsProjectDetailInvest(CsProjectDetailInvest csProjectDetailInvest) {
		csProjectDetailInvestRepository.save(csProjectDetailInvest);
	}
	
}
