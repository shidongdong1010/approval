package com.itanbank.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsUserBak;
import com.itanbank.account.repository.account.CsCapitalFlowInfoBakRepository;
import com.itanbank.account.repository.account.CsCompanyAccountBakRepository;
import com.itanbank.account.repository.account.CsProjectInfoBakRepository;
import com.itanbank.account.repository.account.CsUserBakRepository;

@Service
public class BackupServiceImpl implements BackupService {
	
	@Autowired
	private CsUserBakRepository csUserBakRepository;
	
	@Autowired
	private CsProjectInfoBakRepository csProjectInfoBakRepository;
	
	@Autowired
	private CsCompanyAccountBakRepository csCompanyAccountBakRepository;
	
	@Autowired
	private CsCapitalFlowInfoBakRepository csCapitalFlowInfoBakRepository;

	@Override
	public void saveCsUserBak(CsUserBak csUserBak) {
		csUserBakRepository.save(csUserBak);
	}

	@Override
	public void backUpUserInfo(String batchNo) throws Exception{
		csUserBakRepository.backUpUserInfo(batchNo);
		
	}

	@Override
	public void backUpProjectInfo(String batchNo) throws Exception{
		
		csProjectInfoBakRepository.backUpProjectInfo(batchNo);
	}

	@Override
	public void backUpCompanyInfo(String batchNo) throws Exception {
		
		csCompanyAccountBakRepository.backUpCompanyInfo(batchNo);
	}

	@Override
	public void backUpCapitalInfo(String batchNo,String date) throws Exception {
		
		csCapitalFlowInfoBakRepository.backUpCapitalInfo(batchNo, date);
	}
	
	
}
