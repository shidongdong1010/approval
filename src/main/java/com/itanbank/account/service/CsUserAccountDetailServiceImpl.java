package com.itanbank.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itanbank.account.domain.account.CsUserAccountDetail;
import com.itanbank.account.repository.account.CsUserAccountDetailRepository;

@Service
public class CsUserAccountDetailServiceImpl implements CsUserAccountDetailService {
	
	@Autowired
	private CsUserAccountDetailRepository csUserAccountDetailRepository;

	@Override
	public void saveCsUserAccountDetail(CsUserAccountDetail csUserAccountDetail) {
		
		csUserAccountDetailRepository.save(csUserAccountDetail);
	}

	@Override
	public List<CsUserAccountDetail> queryCsUserAccountDetailByBatchNo(String batchNo) {
		
		return csUserAccountDetailRepository.queryCsUserAccountDetailByBatchNo(batchNo);
	}
}
